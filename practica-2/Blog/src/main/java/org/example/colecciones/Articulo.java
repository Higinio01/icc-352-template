package org.example.colecciones;

import java.util.Date;
import java.util.List;

public class Articulo {
 long id;
 String titulo;
 String cuerpo;
 Usuario autor;
 Date fecha;
 List<Comentario> ListaComentario;
 List<Etiqueta> ListaEtiqueta;

 public long getId() {
  return id;
 }

 public void setId(long id) {
  this.id = id;
 }

 public String getTitulo() {
  return titulo;
 }

 public void setTitulo(String titulo) {
  this.titulo = titulo;
 }

 public String getCuerpo() {
  return cuerpo;
 }

 public void setCuerpo(String cuerpo) {
  this.cuerpo = cuerpo;
 }


 public Usuario getAutor() {
  return autor;
 }

 public void setAutor(Usuario autor) {
  this.autor = autor;
 }

 public Date getFecha() {
  return fecha;
 }

 public void setFecha(Date fecha) {
  this.fecha = fecha;
 }

 public List<Comentario> getListaComentario() {
  return ListaComentario;
 }

 public void setListaComentario(List<Comentario> listaComentario) {
  ListaComentario = listaComentario;
 }

 public List<Etiqueta> getListaEtiqueta() {
  return ListaEtiqueta;
 }

 public void setListaEtiqueta(List<Etiqueta> listaEtiqueta) {
  ListaEtiqueta = listaEtiqueta;
 }

 public Articulo(long id, String titulo, String cuerpo, Usuario autor, Date fecha, List<Comentario> listaComentario, List<Etiqueta> listaEtiqueta) {
  this.id = id;
  this.titulo = titulo;
  this.cuerpo = cuerpo;
  this.autor = autor;
  this.fecha = fecha;
  ListaComentario = listaComentario;
  ListaEtiqueta = listaEtiqueta;
 }

 public Articulo(long id, String titulo, String cuerpo, Usuario autor, Date fecha) {
  this.id = id;
  this.titulo = titulo;
  this.cuerpo = cuerpo;
  this.autor = autor;
  this.fecha = fecha;
 }
}
