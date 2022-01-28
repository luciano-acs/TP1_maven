/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentador;

import AFIP.AlicIva;
import AFIP.ArrayOfAlicIva;
import AFIP.ArrayOfFECAEDetRequest;
import AFIP.FEAuthRequest;
import AFIP.FECAECabRequest;
import AFIP.FECAEDetRequest;
import AFIP.FECAERequest;
import AFIP.FECAEResponse;
import AFIP.FERecuperaLastCbteResponse;
import AFIP.Service;
import AFIP.ServiceSoap;
import Modelo.BD.BD;
import Modelo.Cliente.Cliente;
import Modelo.Organizacion.Empleado;
import Modelo.Producto.ColorP;
import Modelo.Producto.Producto;
import Modelo.Producto.Talle;
import Modelo.Producto.TipoDeTalle;
import Modelo.Venta.FormaDePago;
import Modelo.Venta.LineaDeVenta;
import Modelo.Venta.Venta;
import Vista.pVentas;
import Vista.vistaMenu;
import Wrapper.Autorizacion;
import Wrapper.ILoginService;
import Wrapper.LoginService;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import static java.awt.event.ItemEvent.SELECTED;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luciano Acosta
 */
public class PresentadorVentas implements ActionListener, java.awt.event.ItemListener{

    vistaMenu menu = new vistaMenu();
    pVentas ventas = new pVentas();
    Venta v = new Venta();
    BD bd = new BD();
    Calendar fecha = new GregorianCalendar();
    int dia = fecha.get(DAY_OF_MONTH);
    int mes = fecha.get(MONTH);
    int anio = fecha.get(YEAR);
    String GUID = "036F676C-6D35-4CEE-B315-DA9D55C43A10";

    public PresentadorVentas(pVentas vista, vistaMenu menu) {
        this.ventas = vista;
        this.menu = menu;
        
        ventas.btnBuscarCliente.addActionListener(this);
        ventas.btnBuscarP.addActionListener(this);
        ventas.btnConfirmar.addActionListener(this);
        ventas.btnEliminar.addActionListener(this);
        ventas.cbTipo.addItemListener(this);
        ventas.cbPago.addItemListener(this);
        ventas.btnCancelarVenta.addActionListener(this);
        ventas.btnRegistarVenta.addActionListener(this);
        ventas.btnFinalizarVenta.addActionListener(this);
        
        ventas.jtfMonto.setEnabled(false);
        ventas.cbPago.setEnabled(true);
        ventas.jtfCVV.setEnabled(false);
        ventas.jtfTarjeta.setEnabled(false);
        ventas.jtfVto.setEnabled(false);
    }  
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(ventas.btnBuscarCliente)){
            buscarCliente();
            Cliente cl = buscarCliente();
            cardarId(cl);            
        }
        if(e.getSource().equals(ventas.btnBuscarP)){
            if(bd.existe(ventas.jtfNombre.getText())){
                if(bd.consultarStock(ventas.jtfNombre.getText()) > 0){
                    buscarProducto();
                }else{
                    showMessageDialog(null, "Producto sin stock");
                }
            }else{
                showMessageDialog(null, "El producto no exite");
                ventas.jtfNombre.setText("");
            }
        }
        if(e.getSource().equals(ventas.btnConfirmar)){
            int codColor = bd.buscarCodColor((String) ventas.cbColor.getSelectedItem());
            int codTalle = bd.buscarCodTalle((String) ventas.cbTalle.getSelectedItem());
            if(parseInt(ventas.jtfCantidad.getText()) <= bd.consultarStock(ventas.jtfNombre.getText())){
                if(bd.cantColor(ventas.jtfNombre.getText(),codColor) >= parseInt(ventas.jtfCantidad.getText()) &&
                   bd.cantTalle(ventas.jtfNombre.getText(),codTalle) >= parseInt(ventas.jtfCantidad.getText())){
                    
                    String codigo = ventas.jtfNombre.getText();
                    String descripcion = ventas.jtfDescripcion.getText();
                    int cant = parseInt(ventas.jtfCantidad.getText());
            
                    Producto p = bd.buscarProducto(codigo);    
                    Talle t = new Talle(bd.buscarCodTalle((String) ventas.cbTalle.getSelectedItem()),(String) ventas.cbTalle.getSelectedItem());
                    ColorP co = new ColorP(bd.buscarCodColor((String) ventas.cbColor.getSelectedItem()),(String) ventas.cbColor.getSelectedItem());
            
                    double precio = p.calcularPrecio(p.getCosto(), p.getPorcIVA(), p.getMargenGanancia());;
                    double subtotal = cant * precio;            
            
                    DefaultTableModel datos = (DefaultTableModel) ventas.jtLinea.getModel();        
                    Object[] fila = {codigo,descripcion,cant,precio,subtotal,t.getIdTalle(),co.getIdColor()};                
                    datos.addRow(fila); 
            
                    total();
                    limpiar();
                    bd.actualizarStock(parseInt(codigo),cant,parseInt(ventas.jtfID.getText()),t,co);                    
                }else{
                    showMessageDialog(null, "Stock no disponible. Las "+ventas.jtfDescripcion.getText()+" en stock para dicho talle y color son: "+bd.cantColor(ventas.jtfNombre.getText(),codColor));
                }                
            }else{
                showMessageDialog(null, "Stock no disponible. Las "+ventas.jtfDescripcion.getText()+" en stock son: "+bd.consultarStock(ventas.jtfNombre.getText()));
            }
        }
        if(e.getSource().equals(ventas.btnEliminar)){
            DefaultTableModel datos = (DefaultTableModel) ventas.jtLinea.getModel();
            int fila = ventas.jtLinea.getSelectedRow();
            int linea = parseInt(ventas.jtfID.getText());
            
            String codigo = (String) ventas.jtLinea.getValueAt(fila, 0);
            int cant = (int) ventas.jtLinea.getValueAt(fila, 2);
            Talle t = new Talle((int) ventas.jtLinea.getValueAt(fila, 5),bd.buscarDTalle((int) ventas.jtLinea.getValueAt(fila, 5)));
            ColorP co = new ColorP((int) ventas.jtLinea.getValueAt(fila, 6),bd.buscarDColor((int) ventas.jtLinea.getValueAt(fila, 6)));
            
            bd.restaurarStock(codigo,cant,t,co,linea);            
            
            datos.removeRow(fila);
            total();
        }
        if(e.getSource().equals(ventas.btnRegistarVenta)){
            
            cargarVenta();            
            
                                    
        }
        if(e.getSource().equals(ventas.btnCancelarVenta)){
            DefaultTableModel datos = (DefaultTableModel) ventas.jtLinea.getModel();
            int linea = parseInt(ventas.jtfID.getText());
            
            for(int i=0;i<datos.getRowCount();i++){
                String codigo = (String) ventas.jtLinea.getValueAt(i, 0);
                int cant = (int) ventas.jtLinea.getValueAt(i, 2);
                Talle t = new Talle((int) ventas.jtLinea.getValueAt(i, 5),bd.buscarDTalle((int) ventas.jtLinea.getValueAt(i, 5)));
                ColorP co = new ColorP((int) ventas.jtLinea.getValueAt(i, 6),bd.buscarDColor((int) ventas.jtLinea.getValueAt(i, 6)));
            
                bd.restaurarStock(codigo,cant,t,co,linea);
            }
            datos.setRowCount(0);
            for(int i=0;i<datos.getRowCount();i++){
                datos.removeRow(i);
            }
            
            limpiar();
            total();
            showMessageDialog(null, "Venta cancelada");
        }
        if(e.getSource().equals(ventas.btnFinalizarVenta)){
            limpiar();      
        }
    }
    
    private void total() {
        if(ventas.jtLinea.getRowCount()>0){
            double sum = 0;
            for(int i=0;i<ventas.jtLinea.getRowCount();i++){
                sum += (double) ventas.jtLinea.getValueAt(i, 4);
                ventas.jlTotal.setText(""+sum);
            }
        }else{
            ventas.jlTotal.setText(""+0);
        }
    }
//    
    private void cargarVenta() {
        
        String fecha = menu.jlFecha.getText();
        int comprobante = parseInt(ventas.jtfID.getText());
        double total = parseDouble(ventas.jlTotal.getText());
        Cliente cl = bd.buscarCliente(ventas.jtfCUIT.getText());        
        
        ArrayList<LineaDeVenta> lista = new ArrayList<>();
        DefaultTableModel datos = (DefaultTableModel) ventas.jtLinea.getModel();  
        for(int i = 0;i<ventas.jtLinea.getRowCount();i++){
            LineaDeVenta li = new LineaDeVenta();
            li.setCantidad((int) datos.getValueAt(i, 2));
            
            Producto p = bd.buscarProducto(ventas.jtLinea.getValueAt(i, 0).toString());

            li.setProducto(p);
            lista.add(li);
        }                   
            
        Venta ve = new Venta(fecha,comprobante,total,cl,lista);
        FECAEResponse respuesta = autorizacionAFIP(ve);
        
        if(respuesta.getFeCabResp().getResultado().equals("A")){
            bd.registrarVenta(ve,parseInt(menu.jlPunto.getText()));        
        
            ventas.jtfMonto.setText(ventas.jlTotal.getText());
            limpiar();
            datos.setRowCount(0);
            for(int i=0;i<datos.getRowCount();i++){
                datos.removeRow(i);
            }
            
            showMessageDialog(null,"Venta registrada y aprobada");
            ventas.jtfMonto.setEnabled(true);
            ventas.cbPago.setEnabled(true);
            
            ArrayList<FormaDePago> formas = bd.listarFormas();
            for(int i = 0;i<formas.size();i++){
                ventas.cbPago.addItem(formas.get(i).getDescripcion());
            }
        }else{
            showMessageDialog(null, respuesta.getFeDetResp().getFECAEDetResponse().get(0).getObservaciones());
        }
    }   

    public void cardarId(Cliente cl) {
        
        FEAuthRequest auth = generarAuth();
        int condTrib = 0;
        
        if(cl.getCondicion().equals("RI")||cl.getCondicion().equals("M")){
            condTrib = 1;
        }else{
            condTrib = 6;
        }
        
        Service servicioA = new Service();
        ServiceSoap loginA = servicioA.getServiceSoap();
        FERecuperaLastCbteResponse ultimo = loginA.feCompUltimoAutorizado(auth, 25, condTrib);
        int idVenta = ultimo.getCbteNro()+1;
        
        ventas.jtfID.setHorizontalAlignment(CENTER);
        ventas.jtfID.setText(""+idVenta);
        ventas.jtfID.setEnabled(false);
    }

    private Cliente buscarCliente() {
        String cuit = ventas.jtfCUIT.getText();
        Cliente c = bd.buscarCliente(cuit);
        
        ventas.jtfRazonSocial.setText(c.getRazonSocial());
        return c;
    }

    private void buscarProducto() {
        int cod = parseInt(ventas.jtfNombre.getText());
        Producto p = bd.listarProducto(cod);

        ventas.jtfDescripcion.setText(p.getDescripcion());
        ventas.jtfMarca.setText(p.getMarca().getDescripcionM());
        ventas.cbColor.removeAllItems();;
        ventas.cbTipo.removeAllItems();
        ventas.cbTalle.removeAllItems();
        
        ArrayList<TipoDeTalle> tipo = bd.listarStockTipoTalle(cod);
        ArrayList<Modelo.Producto.ColorP> color = bd.listarStockColor(cod);
        
        for(int i =0;i<tipo.size();i++){
            ventas.cbTipo.addItem(tipo.get(i).getDescripcion());
        }
        for(int i =0;i<color.size();i++){
            ventas.cbColor.addItem(color.get(i).getDescripcion());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==SELECTED){
            ventas.cbTalle.removeAllItems();
            if(ventas.cbTipo.getSelectedIndex()>-1){
                int cod = bd.buscarCodTipo(ventas.cbTipo.getSelectedItem().toString());        
                ArrayList<Talle> talle =  bd.buscarStockTalle(cod,ventas.cbTipo.getSelectedItem().toString());
                for(int i =0;i<talle.size();i++){
                    ventas.cbTalle.addItem(talle.get(i).getDescripcion());            
                }
            }
        }
        if(e.getStateChange()==SELECTED){
            if(ventas.cbPago.getSelectedIndex()>-1){
                if(ventas.cbPago.getSelectedItem().equals("tarjeta")){
                    ventas.jtfTarjeta.setEnabled(true);
                    ventas.jtfVto.setEnabled(true);
                    ventas.jtfCVV.setEnabled(true);
                }else{
                    ventas.jtfTarjeta.setEnabled(false);
                    ventas.jtfVto.setEnabled(false);
                    ventas.jtfCVV.setEnabled(false);
                }
            }
        }
    }

    //limpiar jtf venta
    private void limpiar() {
        ventas.jtfCantidad.setText("");
        ventas.jtfMarca.setText("");
        ventas.jtfDescripcion.setText("");
        ventas.jtfNombre.setText("");
        ventas.cbColor.removeAllItems();
        ventas.cbTipo.removeAllItems();
        ventas.cbTalle.removeAllItems();
        ventas.cbPago.removeAllItems();
    }

    private FECAEResponse autorizacionAFIP(Venta ve) {
        boolean estado = false;
        int condTrib = 0;
        double sneto = 0;
        
        FEAuthRequest auth = generarAuth();
                
        //condicion tributaria
        if(ve.getCliente().getCondicion().equals("RI")||ve.getCliente().getCondicion().equals("M")){
            condTrib = 1;
        }else{
            condTrib = 6;
        }
        
        //neto e iva
        for(int i=0; i<ve.getLista().size();i++){
            double neto = ve.getLista().get(i).getProducto().getCosto() + (ve.getLista().get(i).getProducto().getCosto()*ve.getLista().get(i).getProducto().getMargenGanancia());
            sneto = sneto+neto;
        }        
        
        //afip
        Service servicioA = new Service();
        ServiceSoap loginA = servicioA.getServiceSoap();
        FERecuperaLastCbteResponse ultimo = loginA.feCompUltimoAutorizado(auth, 25, condTrib);        
        
        FECAECabRequest feCabReq = new FECAECabRequest();
        feCabReq.setPtoVta(25); //constante
        feCabReq.setCbteTipo(condTrib); //variable
        feCabReq.setCantReg(1); //constante?
        
        ArrayOfAlicIva iva = new ArrayOfAlicIva();
        AlicIva aliciva = new AlicIva();
        aliciva.setId(5); //constante?
        aliciva.setBaseImp(sneto); //variable
        aliciva.setImporte(sneto*0.21); //variable
        iva.getAlicIva().add(aliciva);
        
        ArrayOfFECAEDetRequest feDetReq = new ArrayOfFECAEDetRequest();
        FECAEDetRequest det = new FECAEDetRequest();
        det.setConcepto(1); //constante
        det.setDocTipo(80); //constante 
        det.setDocNro(Long.parseLong(ve.getCliente().getCuit())); 
        det.setCbteDesde(ultimo.getCbteNro()+1);
        det.setCbteHasta(ultimo.getCbteNro()+1);
        det.setCbteFch(anio+""+(mes+1)+""+dia);
        det.setImpTotal(ve.getTotal());
        det.setImpNeto(sneto);
        det.setImpIVA(sneto*0.21);
        det.setMonId("PES");
        det.setMonCotiz(1);
        det.setIva(iva);
        feDetReq.getFECAEDetRequest().add(det);
                
        FECAERequest feCAEReq = new FECAERequest();
        feCAEReq.setFeCabReq(feCabReq);
        feCAEReq.setFeDetReq(feDetReq);
                
        FECAEResponse solicitar = loginA.fecaeSolicitar(auth, feCAEReq);
                
        return solicitar;
    }

    private FEAuthRequest generarAuth() {
        //wrapper
        LoginService servicio = new LoginService();
        ILoginService login = servicio.getSGEAuthService();
        Autorizacion autorizacion = login.solicitarAutorizacion(GUID);        
        
        FEAuthRequest auth = new FEAuthRequest();
        auth.setCuit(autorizacion.getCuit());
        auth.setSign(autorizacion.getSign().getValue());
        auth.setToken(autorizacion.getToken().getValue());
        String error = autorizacion.getError().getValue();
        
        return auth;
    }
}
