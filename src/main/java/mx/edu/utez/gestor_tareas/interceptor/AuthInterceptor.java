package mx.edu.utez.gestor_tareas.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        
        String path = request.getRequestURI();
        
        if (path.equals("/login") || path.equals("/registro") || 
            path.startsWith("/css/") || path.startsWith("/js/") ||
            path.startsWith("/api/login") || path.startsWith("/api/registro")) {
            return true;
        }
        
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
}

