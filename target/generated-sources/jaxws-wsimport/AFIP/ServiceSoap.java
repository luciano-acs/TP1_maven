
package AFIP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ServiceSoap", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServiceSoap {


    /**
     * Solicitud de Código de Autorización Electrónico (CAE)
     * 
     * @param feCAEReq
     * @param auth
     * @return
     *     returns AFIP.FECAEResponse
     */
    @WebMethod(operationName = "FECAESolicitar", action = "http://ar.gov.afip.dif.FEV1/FECAESolicitar")
    @WebResult(name = "FECAESolicitarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAESolicitar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAESolicitar")
    @ResponseWrapper(localName = "FECAESolicitarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAESolicitarResponse")
    public FECAEResponse fecaeSolicitar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "FeCAEReq", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FECAERequest feCAEReq);

    /**
     * Retorna la cantidad maxima de registros que puede tener una invocacion al metodo FECAESolicitar / FECAEARegInformativo 
     * 
     * @param auth
     * @return
     *     returns AFIP.FERegXReqResponse
     */
    @WebMethod(operationName = "FECompTotXRequest", action = "http://ar.gov.afip.dif.FEV1/FECompTotXRequest")
    @WebResult(name = "FECompTotXRequestResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECompTotXRequest", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECompTotXRequest")
    @ResponseWrapper(localName = "FECompTotXRequestResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECompTotXRequestResponse")
    public FERegXReqResponse feCompTotXRequest(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Metodo dummy para verificacion de funcionamiento
     * 
     * @return
     *     returns AFIP.DummyResponse
     */
    @WebMethod(operationName = "FEDummy", action = "http://ar.gov.afip.dif.FEV1/FEDummy")
    @WebResult(name = "FEDummyResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEDummy", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEDummy")
    @ResponseWrapper(localName = "FEDummyResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEDummyResponse")
    public DummyResponse feDummy();

    /**
     * Retorna el ultimo comprobante autorizado para el tipo de comprobante / cuit / punto de venta ingresado / Tipo de Emisión
     * 
     * @param cbteTipo
     * @param auth
     * @param ptoVta
     * @return
     *     returns AFIP.FERecuperaLastCbteResponse
     */
    @WebMethod(operationName = "FECompUltimoAutorizado", action = "http://ar.gov.afip.dif.FEV1/FECompUltimoAutorizado")
    @WebResult(name = "FECompUltimoAutorizadoResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECompUltimoAutorizado", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECompUltimoAutorizado")
    @ResponseWrapper(localName = "FECompUltimoAutorizadoResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECompUltimoAutorizadoResponse")
    public FERecuperaLastCbteResponse feCompUltimoAutorizado(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "PtoVta", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int ptoVta,
        @WebParam(name = "CbteTipo", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int cbteTipo);

    /**
     * Consulta Comprobante emitido y su código.
     * 
     * @param auth
     * @param feCompConsReq
     * @return
     *     returns AFIP.FECompConsultaResponse
     */
    @WebMethod(operationName = "FECompConsultar", action = "http://ar.gov.afip.dif.FEV1/FECompConsultar")
    @WebResult(name = "FECompConsultarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECompConsultar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECompConsultar")
    @ResponseWrapper(localName = "FECompConsultarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECompConsultarResponse")
    public FECompConsultaResponse feCompConsultar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "FeCompConsReq", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FECompConsultaReq feCompConsReq);

    /**
     * Rendición de comprobantes asociados a un CAEA.
     * 
     * @param auth
     * @param feCAEARegInfReq
     * @return
     *     returns AFIP.FECAEAResponse
     */
    @WebMethod(operationName = "FECAEARegInformativo", action = "http://ar.gov.afip.dif.FEV1/FECAEARegInformativo")
    @WebResult(name = "FECAEARegInformativoResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEARegInformativo", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEARegInformativo")
    @ResponseWrapper(localName = "FECAEARegInformativoResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEARegInformativoResponse")
    public FECAEAResponse fecaeaRegInformativo(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "FeCAEARegInfReq", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FECAEARequest feCAEARegInfReq);

    /**
     * Solicitud de Código de Autorización Electrónico Anticipado (CAEA)
     * 
     * @param auth
     * @param periodo
     * @param orden
     * @return
     *     returns AFIP.FECAEAGetResponse
     */
    @WebMethod(operationName = "FECAEASolicitar", action = "http://ar.gov.afip.dif.FEV1/FECAEASolicitar")
    @WebResult(name = "FECAEASolicitarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEASolicitar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEASolicitar")
    @ResponseWrapper(localName = "FECAEASolicitarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEASolicitarResponse")
    public FECAEAGetResponse fecaeaSolicitar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "Periodo", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int periodo,
        @WebParam(name = "Orden", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        short orden);

    /**
     * Consulta CAEA informado como sin movimientos.
     * 
     * @param caea
     * @param auth
     * @param ptoVta
     * @return
     *     returns AFIP.FECAEASinMovConsResponse
     */
    @WebMethod(operationName = "FECAEASinMovimientoConsultar", action = "http://ar.gov.afip.dif.FEV1/FECAEASinMovimientoConsultar")
    @WebResult(name = "FECAEASinMovimientoConsultarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEASinMovimientoConsultar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEASinMovimientoConsultar")
    @ResponseWrapper(localName = "FECAEASinMovimientoConsultarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEASinMovimientoConsultarResponse")
    public FECAEASinMovConsResponse fecaeaSinMovimientoConsultar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "CAEA", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        String caea,
        @WebParam(name = "PtoVta", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int ptoVta);

    /**
     * Informa CAEA sin movimientos.
     * 
     * @param caea
     * @param auth
     * @param ptoVta
     * @return
     *     returns AFIP.FECAEASinMovResponse
     */
    @WebMethod(operationName = "FECAEASinMovimientoInformar", action = "http://ar.gov.afip.dif.FEV1/FECAEASinMovimientoInformar")
    @WebResult(name = "FECAEASinMovimientoInformarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEASinMovimientoInformar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEASinMovimientoInformar")
    @ResponseWrapper(localName = "FECAEASinMovimientoInformarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEASinMovimientoInformarResponse")
    public FECAEASinMovResponse fecaeaSinMovimientoInformar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "PtoVta", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int ptoVta,
        @WebParam(name = "CAEA", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        String caea);

    /**
     * Consultar CAEA emitidos.
     * 
     * @param auth
     * @param periodo
     * @param orden
     * @return
     *     returns AFIP.FECAEAGetResponse
     */
    @WebMethod(operationName = "FECAEAConsultar", action = "http://ar.gov.afip.dif.FEV1/FECAEAConsultar")
    @WebResult(name = "FECAEAConsultarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEAConsultar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEAConsultar")
    @ResponseWrapper(localName = "FECAEAConsultarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FECAEAConsultarResponse")
    public FECAEAGetResponse fecaeaConsultar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "Periodo", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int periodo,
        @WebParam(name = "Orden", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        short orden);

    /**
     * Recupera la cotizacion de la moneda consultada y su  fecha 
     * 
     * @param monId
     * @param auth
     * @return
     *     returns AFIP.FECotizacionResponse
     */
    @WebMethod(operationName = "FEParamGetCotizacion", action = "http://ar.gov.afip.dif.FEV1/FEParamGetCotizacion")
    @WebResult(name = "FEParamGetCotizacionResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetCotizacion", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetCotizacion")
    @ResponseWrapper(localName = "FEParamGetCotizacionResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetCotizacionResponse")
    public FECotizacionResponse feParamGetCotizacion(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "MonId", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        String monId);

    /**
     * Recupera el listado de los diferente tributos que pueden ser utilizados  en el servicio de autorizacion
     * 
     * @param auth
     * @return
     *     returns AFIP.FETributoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposTributos", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposTributos")
    @WebResult(name = "FEParamGetTiposTributosResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposTributos", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposTributos")
    @ResponseWrapper(localName = "FEParamGetTiposTributosResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposTributosResponse")
    public FETributoResponse feParamGetTiposTributos(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de monedas utilizables en servicio de autorización
     * 
     * @param auth
     * @return
     *     returns AFIP.MonedaResponse
     */
    @WebMethod(operationName = "FEParamGetTiposMonedas", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposMonedas")
    @WebResult(name = "FEParamGetTiposMonedasResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposMonedas", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposMonedas")
    @ResponseWrapper(localName = "FEParamGetTiposMonedasResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposMonedasResponse")
    public MonedaResponse feParamGetTiposMonedas(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de Tipos de Iva utilizables en servicio de autorización.
     * 
     * @param auth
     * @return
     *     returns AFIP.IvaTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposIva", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposIva")
    @WebResult(name = "FEParamGetTiposIvaResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposIva", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposIva")
    @ResponseWrapper(localName = "FEParamGetTiposIvaResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposIvaResponse")
    public IvaTipoResponse feParamGetTiposIva(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de identificadores para los campos Opcionales
     * 
     * @param auth
     * @return
     *     returns AFIP.OpcionalTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposOpcional", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposOpcional")
    @WebResult(name = "FEParamGetTiposOpcionalResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposOpcional", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposOpcional")
    @ResponseWrapper(localName = "FEParamGetTiposOpcionalResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposOpcionalResponse")
    public OpcionalTipoResponse feParamGetTiposOpcional(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de identificadores para el campo Concepto.
     * 
     * @param auth
     * @return
     *     returns AFIP.ConceptoTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposConcepto", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposConcepto")
    @WebResult(name = "FEParamGetTiposConceptoResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposConcepto", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposConcepto")
    @ResponseWrapper(localName = "FEParamGetTiposConceptoResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposConceptoResponse")
    public ConceptoTipoResponse feParamGetTiposConcepto(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de puntos de venta registrados y su estado
     * 
     * @param auth
     * @return
     *     returns AFIP.FEPtoVentaResponse
     */
    @WebMethod(operationName = "FEParamGetPtosVenta", action = "http://ar.gov.afip.dif.FEV1/FEParamGetPtosVenta")
    @WebResult(name = "FEParamGetPtosVentaResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetPtosVenta", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetPtosVenta")
    @ResponseWrapper(localName = "FEParamGetPtosVentaResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetPtosVentaResponse")
    public FEPtoVentaResponse feParamGetPtosVenta(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de Tipos de Comprobantes utilizables en servicio de autorización.
     * 
     * @param auth
     * @return
     *     returns AFIP.CbteTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposCbte", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposCbte")
    @WebResult(name = "FEParamGetTiposCbteResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposCbte", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposCbte")
    @ResponseWrapper(localName = "FEParamGetTiposCbteResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposCbteResponse")
    public CbteTipoResponse feParamGetTiposCbte(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de Tipos de Documentos utilizables en servicio de autorización.
     * 
     * @param auth
     * @return
     *     returns AFIP.DocTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposDoc", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposDoc")
    @WebResult(name = "FEParamGetTiposDocResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposDoc", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposDoc")
    @ResponseWrapper(localName = "FEParamGetTiposDocResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposDocResponse")
    public DocTipoResponse feParamGetTiposDoc(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de los diferente paises que pueden ser utilizados  en el servicio de autorizacion
     * 
     * @param auth
     * @return
     *     returns AFIP.FEPaisResponse
     */
    @WebMethod(operationName = "FEParamGetTiposPaises", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposPaises")
    @WebResult(name = "FEParamGetTiposPaisesResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposPaises", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposPaises")
    @ResponseWrapper(localName = "FEParamGetTiposPaisesResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "AFIP.FEParamGetTiposPaisesResponse")
    public FEPaisResponse feParamGetTiposPaises(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

}
