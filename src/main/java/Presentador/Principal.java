/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentador;

import AFIP.AlicIva;
import AFIP.ArrayOfAlicIva;
import AFIP.ArrayOfFECAEDetRequest;
import AFIP.DummyResponse;
import AFIP.FEAuthRequest;
import AFIP.FECAEADetRequest;
import AFIP.FECAECabRequest;
import AFIP.FECAEDetRequest;
import AFIP.FECAERequest;
import AFIP.FECAEResponse;
import AFIP.FECAESolicitar;
import AFIP.FECompUltimoAutorizado;
import AFIP.FEDetRequest;
import AFIP.FEDummy;
import AFIP.FERecuperaLastCbteResponse;
import AFIP.Service;
import AFIP.ServiceSoap;
import Vista.Sesion;
import Vista.vistaMenu;
import Wrapper.Autorizacion;
import Wrapper.ILoginService;
import Wrapper.LoginService;
import Wrapper.ObjectFactory;
import Wrapper.SolicitarAutorizacion;
import java.io.File;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
/**
 *
 * @author Luciano Acosta
 */
public class Principal{
    
    public static void main(String[] args) throws JAXBException {        
        Sesion inicio = new Sesion();
        PresentadorInicio iniciar = new PresentadorInicio(inicio);
        
      
//        DummyResponse comprobar = loginA.feDummy();
        
        
        
        //http://istp1service.azurewebsites.net/LoginService.svc?wsdl
        //https://wswhomo.afip.gov.ar/wsfev1/service.asmx?WSDL
        
    }
}
