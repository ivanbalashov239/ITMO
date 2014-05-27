import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MinimalServlets {
    private static Map<String, Entity> entities = new HashMap<>();
    private static int idCounter = 0;
 
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(Squares.class, "/squares/*");
        server.start();
        server.join();
    }
 
    @SuppressWarnings("serial")
    public static class Squares extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String[] pathSplit = request.getPathInfo() == null? new String[0] : request.getPathInfo().split("/");
            if (pathSplit.length<2) {
                //It can be static, but i'm lazy
                String page = "﻿<!DOCTYPE html>\n" +
                        "<html>\n" +
                        " <head>\n" +
                        "   <meta http-equiv=\"Content-Language\" content=\"ru\">\n" +
                        "   <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "   <style>\n" +
                        "     #field {\n" +
                        "       height: 100%;\n" +
                        "       width: 100%;\n" +
                        "       position: fixed;\n" +
                        "       margin: 10px 0;\n" +
                        "       border: solid black thin;\n" +
                        "     }\n" +
                        "   </style>\n" +
                        " </head>\n" +
                        " <body>\n" +
                        "   <div id=\"field\">\n";


                for (Entity e : entities.values()) {
                    page+=e;
                }

                page += "   </div>\n" +
                        " </body>\n" +
                        "</html>";

                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(page);
                response.setStatus(HttpServletResponse.SC_OK);

            } else if (pathSplit.length==2&&entities.containsKey(pathSplit[1])) {
                Entity e = entities.get(pathSplit[1]);
                response.getWriter().println("Square "+ e.id +"\n" +
                        "x: "+e.x+"\n" +
                        "y: "+e.y+"\n" +
                        "size: "+e.size+"\n" +
                        "color: "+e.color+"\n");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getWriter().println("Bad request");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            //response.getWriter().println("<h1>HAHAHA</h1>");
            Map<String, String[]> params = request.getParameterMap();
            boolean isOkay = true;
            int x=0, y=0, size=0;
            String color=null;
            try {
                x = Integer.parseInt(params.get("x")[0]);
            } catch (NumberFormatException e) {
                response.getWriter().println("Wrong x param: "+params.get("x")[0]);
                isOkay = false;
            } catch (NullPointerException e) {
                response.getWriter().println("You should set \"x\" param");
                isOkay = false;
            }

            try {
                y = Integer.parseInt(params.get("y")[0]);
            } catch (NumberFormatException e) {
                response.getWriter().println("Wrong y param: "+params.get("y")[0]);
                isOkay = false;
            } catch (NullPointerException e) {
                response.getWriter().println("You should set \"y\" param");
                isOkay = false;
            }

            try {
                size = Integer.parseInt(params.get("size")[0]);
            } catch (NumberFormatException e) {
                response.getWriter().println("Wrong size param: "+params.get("size")[0]);
                isOkay = false;
            } catch (NullPointerException e) {
                response.getWriter().println("You should set \"size\" param");
                isOkay = false;
            }

            try {
                color = params.get("color")[0];
            } catch (NullPointerException e) {
                response.getWriter().println("You should set \"color\" param");
                isOkay = false;
            }

            if (isOkay) {
                System.out.println(x+" "+y+" "+size);
                entities.put(Integer.toString(idCounter), new Entity(x, y, size, color));
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        @Override
        protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String[] pathSplit = request.getPathInfo() == null? new String[0] : request.getPathInfo().split("/");
            if (pathSplit.length==2&&entities.containsKey(pathSplit[1])) {
                Map<String, String[]> params = request.getParameterMap();
                if (params.containsKey("x")) {
                    try {
                        entities.get(pathSplit[1]).x = Integer.parseInt(params.get("x")[0]);
                    } catch (NumberFormatException e) {response.getWriter().println("Wrong x param: "+params.get("x")[0]);}
                }
                if (params.containsKey("y")) {
                    try {
                        entities.get(pathSplit[1]).y = Integer.parseInt(params.get("y")[0]);
                    } catch (NumberFormatException e) {response.getWriter().println("Wrong y param: "+params.get("y")[0]);}
                }
                if (params.containsKey("size")) {
                    try {
                        entities.get(pathSplit[1]).size = Integer.parseInt(params.get("size")[0]);
                    } catch (NumberFormatException e) {response.getWriter().println("Wrong y param: "+params.get("size")[0]);}
                }
                if (params.containsKey("color")) {
                    entities.get(pathSplit[1]).color = params.get("color")[0];
                }
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getWriter().println("Square not found");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        @Override
        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String[] pathSplit = request.getPathInfo() == null? new String[0] : request.getPathInfo().split("/");
            if (pathSplit.length == 2 && entities.containsKey(pathSplit[1])) {
                entities.remove(pathSplit[1]);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getWriter().println("Square not found");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    public static class Entity{
        int x;
        int y;
        int size;
        String color;
        int id;

        public Entity(int x, int y, int size, String color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            this.id = idCounter++;
        }

        @Override
        public String toString() {
            String result = "<div style=\"" +
                    "position: absolute; " +
                    "width: "+size+"px;" +
                    "height: "+size+"px;" +
                    "background-color: "+color+";" +
                    "left: "+x+"px;" +
                    "top: "+y+"px; \">" +

                    id +

                    "</div>\n";

            return result;
        }
    }
}