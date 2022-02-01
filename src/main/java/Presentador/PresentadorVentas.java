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
import Modelo.Organizacion.Afip;
import Modelo.Producto.ColorP;
import Modelo.Producto.Producto;
import Modelo.Producto.Talle;
import Modelo.Producto.TipoDeTalle;
import Modelo.Venta.Factura;
import Modelo.Venta.FormaDePago;
import Modelo.Venta.LineaDeVenta;
import Modelo.Venta.Pago;
import Modelo.Venta.Venta;
import Vista.nvoCliente;
import Vista.pDevoluciones;
import Vista.pFacturas;
import Vista.pListarVentas;
import Vista.pVentas;
import Vista.vistaMenu;
import Wrapper.Autorizacion;
import Wrapper.ILoginService;
import Wrapper.LoginService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import static java.awt.event.ItemEvent.SELECTED;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luciano Acosta
 */
public class PresentadorVentas implements ActionListener, java.awt.event.ItemListener{

    vistaMenu menu = new vistaMenu();
    pVentas ventas = new pVentas();
    pFacturas factura = new pFacturas();
    Venta v = new Venta();
    BD bd = new BD();
    nvoCliente nvoCliente = new nvoCliente();
    pDevoluciones devolucion = new pDevoluciones();
    pListarVentas listaVentas = new pListarVentas();
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
    
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
        
        factura.btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imprimir-contorno-del-boton.png")));
        factura.btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cerrar-sesion.png")));
    } 

    PresentadorVentas() {
        
    }

    PresentadorVentas(pDevoluciones devolucion) {
        this.devolucion = devolucion;
        
    }

    PresentadorVentas(pListarVentas lista) {
        this.listaVentas = lista;
        listaVentas.btnBuscarV.addActionListener(this);
        listaVentas.btnMostrarDetalles.addActionListener(this);
        listaVentas.cbTipoBusq.addItemListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(ventas.btnBuscarCliente)){
            buscarCliente();
            Cliente cl = buscarCliente();   
            if(cl.getCuit()==null){
                int resp = JOptionPane.showConfirmDialog(null,"Desea registrar un nuevo clientes?");
                if(JOptionPane.OK_OPTION==resp){
                    PresentadorClientes pc = new PresentadorClientes(nvoCliente);
                    pc.cargarCombo();
                    nvoCliente.setVisible(true);
                }
            }
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
            if(Integer.parseInt(ventas.jtfCantidad.getText()) <= bd.consultarStockParticular(ventas.jtfNombre.getText(),codColor,codTalle)){
                
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
//                limpiar();
                bd.actualizarStock(parseInt(codigo),cant,parseInt(ventas.jtfID.getText()),t,co,subtotal);                    
            }else{
                showMessageDialog(null, "Stock no disponible. En stock para dicho talle y color son: "+bd.consultarStockParticular(ventas.jtfNombre.getText(),codColor,codTalle));
            } 
        }
        if(e.getSource().equals(ventas.btnEliminar)){
            DefaultTableModel datos = (DefaultTableModel) ventas.jtLinea.getModel();
            if(datos.getRowCount()!=0){
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
        }
        if(e.getSource().equals(ventas.btnRegistarVenta)){
            if(ventas.jtfNombre.getText().equals("")||ventas.jtfRazonSocial.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Campos sin completar");
            }else{
                ventas.btnCancelarVenta.setEnabled(false);
                cargarVenta();
            }
        }
        if(e.getSource().equals(ventas.btnCancelarVenta)){
            cancelarVenta();            
        }
        if(e.getSource().equals(ventas.btnFinalizarVenta)){
            Venta ve = bd.buscarVenta(Integer.parseInt(ventas.jtfID.getText()));
            
            FormaDePago fdp = bd.buscarFormaPago(ventas.cbPago.getSelectedItem().toString());
            Pago pago = new Pago(Integer.parseInt(ventas.jtfID.getText())
                                 ,Double.parseDouble(ventas.jlTotal.getText()),fdp,ve);
            bd.registrarPago(pago);
            
            PresentadorFactura pf = new PresentadorFactura(factura);           
            
            factura.setVisible(true);
            cardarId();
        }
        if(e.getSource().equals(listaVentas.btnBuscarV)){
            if("".equals(listaVentas.jtfCodigo.getText())||"".equals(listaVentas.jtfCuit.getText())){
                JOptionPane.showMessageDialog(null,"Campos sin completar");
            }else{
                String tipo = "";
            
                if(listaVentas.jlTipoBusq.getText().equals("Id Venta")){
                    tipo = "a.idVenta";
                } 
                if(listaVentas.jlTipoBusq.getText().equals("Num Factura")){
                    tipo = "b.numFactura";
                }
                if(listaVentas.jlTipoBusq.getText().equals("Fecha")){
                    tipo = "a.fecha";
                }
            
                long cuit = Long.parseLong(listaVentas.jtfCuit.getText());
                int codigoTipo = Integer.parseInt(listaVentas.jtfCodigo.getText());
                ArrayList<Venta> ve = bd.busquedaVenta(cuit,tipo,codigoTipo);
            
                if(ve.isEmpty()){
                    JOptionPane.showMessageDialog(listaVentas, "No existe venta");
                }else{
                    listarVentas(ve);
                }
            }
        }
        if(e.getSource().equals(listaVentas.btnMostrarDetalles)){
            int fila = listaVentas.jtVentas.getSelectedRow();
            long cuit = Long.parseLong(listaVentas.jtfCuit.getText());
            
            DefaultTableModel datos = (DefaultTableModel) listaVentas.jtVentas.getModel();
            int codigo = (int) datos.getValueAt(fila, 0);
            
            ArrayList<Venta> venta = bd.busquedaVenta(cuit,"a.idVenta",codigo);
            Factura fact = bd.buscarFactura(codigo);
            Afip afip = bd.buscarFacturaAfip(fact.getNumFact());
            PresentadorFactura pf = new PresentadorFactura(factura,venta,afip,fact);
            factura.setVisible(true);
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
        
        
        String fecha = dtf.format(LocalDateTime.now());
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
            li.setSubtotal(Double.parseDouble(datos.getValueAt(i, 4).toString()));
            lista.add(li);
        }                   
            
        Venta ve = new Venta(fecha,comprobante,total,cl,lista);
        FECAEResponse respuesta = autorizacionAFIP(ve);
        
        if(respuesta.getFeCabResp().getResultado().equals("A")){
            bd.registrarVenta(ve,25);        
        
            ventas.jtfMonto.setText(ventas.jlTotal.getText());
            
            datos.setRowCount(0);
            for(int i=0;i<datos.getRowCount();i++){
                datos.removeRow(i);
            }
            
            showMessageDialog(null,"Venta registrada y aprobada");
            
            
            ArrayList<FormaDePago> formas = bd.listarFormas();
            for(int i = 0;i<formas.size();i++){
                ventas.cbPago.addItem(formas.get(i).getDescripcion());
            }
            ventas.cbPago.setEnabled(true);
            
            PresentadorFactura pf = new PresentadorFactura(factura,ve,respuesta,ventas.cbPago.getSelectedItem().toString());
            Factura fact = new Factura(Math.toIntExact(respuesta.getFeDetResp().getFECAEDetResponse().get(0).getCbteDesde()),
                                       respuesta.getFeDetResp().getFECAEDetResponse().get(0).getCbteFch(),
                                       ve.getTotal(),ve);
            bd.registrarFactura(fact);   
            bd.registarFacturaAfip(fact.getNumFact(),respuesta.getFeDetResp().getFECAEDetResponse().get(0).getCAE(),
                                    respuesta.getFeDetResp().getFECAEDetResponse().get(0).getCAEFchVto(),
                                    ventas.cbPago.getSelectedItem().toString());
        }else{
            cancelarVenta();
//            String mensaje1 = respuesta.getErrors().getErr().get(0).getMsg();
            String mensaje1 = respuesta.getFeDetResp().getFECAEDetResponse().get(0).getObservaciones().getObs().get(0).getMsg();
            String mensaje2 = respuesta.getFeCabResp().getResultado();
//            System.out.println(mensaje2);
            System.out.println(mensaje1);
            showMessageDialog(null, "La venta no fue autorizada: "+mensaje2);
        }
    }   

    public void cardarId() {
        
        int idVenta = bd.ultimoCod();
//        int cod = 1+sum;
//        
//        String fecha = dtf.format(LocalDateTime.now());
//        
//        String cadena = fecha+cod+"";
//        
//        System.out.println(cadena);
//        String idVenta = cadena;
        ventas.jtfID.setHorizontalAlignment(SwingConstants.CENTER);
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
        ventas.cbColor.removeAllItems();
        ventas.cbTipo.removeAllItems();
        ventas.cbTalle.removeAllItems();
        
        ArrayList<TipoDeTalle> tipo = bd.listarStockTipoTalle(cod);
        ArrayList<Modelo.Producto.ColorP> color = bd.listarStockColor(cod);
        
        ventas.cbTipo.setEnabled(true);
        for(int i =0;i<tipo.size();i++){
            if(tipo.get(i) != null){
                ventas.cbTipo.addItem(tipo.get(i).getDescripcion());
            }else{
                ventas.cbTipo.setEnabled(false);
            }
            
        }
        ventas.cbColor.setEnabled(true);
        for(int i =0;i<color.size();i++){
            if(color.get(i)!=null){
                ventas.cbColor.addItem(color.get(i).getDescripcion());
            }else{
                ventas.cbColor.setEnabled(false);
            }            
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==SELECTED){
            ventas.cbTalle.removeAllItems();
            if(ventas.cbTipo.getSelectedIndex()>-1){
                int cod = bd.buscarCodTipo(ventas.cbTipo.getSelectedItem().toString());        
                ArrayList<Talle> talle =  bd.buscarStockTalle(cod,ventas.jtfNombre.getText());
                ventas.cbTalle.setEnabled(true);
                for(int i =0;i<talle.size();i++){
                    if(talle.get(i)!=null){
                        ventas.cbTalle.addItem(talle.get(i).getDescripcion());
                    }else{
                        ventas.cbTalle.setEnabled(false);
                    }
                                
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
        if(e.getStateChange()==SELECTED){
            if(listaVentas.cbTipoBusq.getSelectedIndex()>-1){
                if(listaVentas.cbTipoBusq.getSelectedItem().equals("Id Venta")){
                    listaVentas.jlTipoBusq.setText("Id Venta");
                }
                if(listaVentas.cbTipoBusq.getSelectedItem().equals("Num Factura")){
                    listaVentas.jlTipoBusq.setText("Num Factura");
                }
                if(listaVentas.cbTipoBusq.getSelectedItem().equals("Fecha")){
                    listaVentas.jlTipoBusq.setText("Fecha");
                }                
            }
        }
    }

    private FECAEResponse autorizacionAFIP(Venta ve) {
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
            double costo = ve.getLista().get(i).getProducto().getCosto();
            double margen = ve.getLista().get(i).getProducto().getCosto()*ve.getLista().get(i).getProducto().getMargenGanancia();
            int cantidad = ve.getLista().get(i).getCantidad();
            double neto = (costo+margen)*cantidad;
//            System.out.println("Costo = "+costo);
//            System.out.println("Margen = "+margen);
//            System.out.println("Cantidad = "+cantidad);
//            System.out.println("Neto producto "+i+" = "+neto);
            sneto = sneto+neto;
        }        
        
//        System.out.println("Importe sin impuestos(neto) = " + sneto);
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
        
        
//        System.out.println("Total: " + sneto*1.21);
//        System.out.println("Total en la venta: " + ve.getTotal());
        ArrayOfFECAEDetRequest feDetReq = new ArrayOfFECAEDetRequest();
        FECAEDetRequest det = new FECAEDetRequest();
        det.setConcepto(1); //constante
        det.setDocTipo(80); //constante 
        det.setDocNro(Long.parseLong(ve.getCliente().getCuit())); 
        det.setCbteDesde(ultimo.getCbteNro()+1);
        det.setCbteHasta(ultimo.getCbteNro()+1);
        det.setCbteFch(dtf.format(LocalDateTime.now()));
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
        System.out.println(dtf.format(LocalDateTime.now()));        
        return solicitar;
    }

    public FEAuthRequest generarAuth() {
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

    private void cancelarVenta() {
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
            
//            limpiar();
            total();
            showMessageDialog(null, "Venta cancelada");
    }

    private void listarVentas(ArrayList<Venta> ve) {
        DefaultTableModel datos = (DefaultTableModel) listaVentas.jtVentas.getModel();
        datos.setNumRows(0);
        
        for(int i = 0;i<ve.size();i++){
            Object[] fila = {ve.get(i).getNroComprobante(),
                             ve.get(i).getFecha(),
                             ve.get(i).getTotal(),
                             ve.get(i).getCliente().getCuit()};            
            datos.addRow(fila);
        }
    }
}
