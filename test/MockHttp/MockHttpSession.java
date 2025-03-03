/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomerJUnitPackages;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MockHttpSession implements HttpSession {
    private Map<String, Object> attributes = new HashMap<>();
    
    // Set an attribute in the session
    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
    
    // Get an attribute from the session
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }
    
    // Remove an attribute from the session
    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }
    
    // Get the session ID (can be a mock ID or any value)
    @Override
    public String getId() {
        return "mock-session-id";
    }

    // Other methods in HttpSession are not implemented in this mock
    // We can leave them unimplemented or return default/mock values as needed
    @Override
    public long getCreationTime() {
        return System.currentTimeMillis();
    }

    @Override
    public long getLastAccessedTime() {
        return System.currentTimeMillis();
    }

    @Override
    public void invalidate() {
        // Invalidate the session (clearing attributes)
        attributes.clear();
    }

    @Override
    public boolean isNew() {
        return false; // Mock session is not new
    }

    // Other methods can be implemented as needed, or left unimplemented

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
