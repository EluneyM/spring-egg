package com.egg.demo.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorControlador implements ErrorController {

    @RequestMapping(value = "/error", method = { RequestMethod.GET,
            RequestMethod.POST, RequestMethod.PATCH })
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView("error");
        
        Integer httpErrorCode = getErrorCode(httpRequest);

        String errorMsg = switch (httpErrorCode) {
            case 400 -> "El recurso solicitado no existe.";
            case 403 -> "No tiene permisos para acceder al recurso.";
            case 401 -> "No se encuentra autorizado.";
            case 404 -> "El recurso solicitado no fue encontrado.";
            case 500 -> "Ocurrió un error interno.";
            default -> "Se produjo un error inesperado.";
        };

        // Agrega el código de error y el mensaje de error al modelo para mostrar en la
        // vista
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    // Método para obtener el código de estado HTTP del objeto HttpServletRequest
    private int getErrorCode(HttpServletRequest httpRequest) {
        Object statusCode = httpRequest.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode instanceof Integer) {
            return (Integer) statusCode;
        }
        // Maneja el caso en que el atributo es null o no es un Integer
        return -1; // O puedes usar otro valor por defecto adecuado
    }

    // Método que devuelve la ruta a la página de error
    public String getErrorPath() {
        return "/error.html";
    }
}
