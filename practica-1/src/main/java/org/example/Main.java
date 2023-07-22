package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese la URL (Asegúrese de que incluya el protocolo HTTP/HTTPS)");
            String urlStr = scanner.nextLine();

            try {
                URL url = new URL(urlStr);
                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(urlStr)).build();
                HttpResponse<Void> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());

                String contentType = httpResponse.headers().firstValue("Content-Type").orElse("Tipo del recurso no encontrado");
                System.out.println("Tipo de recurso: " + contentType);

                if (contentType.contains("text/html")) {
                    Document doc = Jsoup.connect(urlStr).get();
                    String html = doc.html();
                    System.out.println("Cantidad de líneas: " + html.lines().count());

                    Elements paragraphs = doc.select("p");
                    System.out.println("Cantidad de párrafos (p): " + paragraphs.size());

                    long imageCount = paragraphs.stream().mapToLong(paragraph -> paragraph.select("img").size()).sum();
                    System.out.println("Cantidad de imágenes en los párrafos: " + imageCount);

                    Elements forms = doc.select("form");
                    System.out.println("Cantidad de formularios (form): " + forms.size());

                    long postCount = forms.stream().filter(form -> form.attr("method").equalsIgnoreCase("post")).count();
                    long getCount = forms.stream().filter(form -> form.attr("method").equalsIgnoreCase("get")).count();
                    System.out.println("Cantidad de formularios por método:");
                    System.out.println("POST: " + postCount);
                    System.out.println("GET: " + getCount);

                    forms.forEach(form -> {
                        System.out.println("Campos de formulario:");
                        form.select("input").forEach(input -> System.out.println("Tipo: " + input.attr("type")));
                    });

                    forms.stream()
                            .filter(form -> form.attr("method").equalsIgnoreCase("post"))
                            .forEach(form -> {
                                try {
                                    Document response = Jsoup.connect(urlStr)
                                            .data("asignatura", "practica1")
                                            .header("matricula-id", "matricula o id asignado")
                                            .post();
                                    System.out.println("Respuesta de la petición POST: " + response.body().text());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
