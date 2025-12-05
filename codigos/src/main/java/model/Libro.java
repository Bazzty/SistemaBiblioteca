package model;

public class Libro {
    private int id;
    private String titulo;
    private int idAutor;      // Clave foránea
    private int idCategoria;  // Clave foránea
    private int stock;

    // --- NUEVOS CAMPOS (Para mostrar nombres en la tabla) ---
    private String nombreAutor;
    private String nombreCategoria;

    public Libro() {}

    public Libro(int id, String titulo, int idAutor, int idCategoria, int stock) {
        this.id = id;
        this.titulo = titulo;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
        this.stock = stock;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getIdAutor() { return idAutor; }
    public void setIdAutor(int idAutor) { this.idAutor = idAutor; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    // --- NUEVOS GETTERS Y SETTERS ---
    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

}