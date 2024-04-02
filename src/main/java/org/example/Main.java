package org.example;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerNum = new Scanner(System.in).useLocale(Locale.US);
    public static void main(String[] args) {
        boolean salir = false;
        int opcion;
        do {
            menuPrincipal();
            opcion = scannerNum.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        agregarCliente();
                        break;
                    case 2:
                        listarCliente();
                        break;
                    case 3:
                        editarCliente();
                        break;
                    case 4:
                        eliminarCliente();
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error: " + e.getMessage());
            }
        } while (!salir);
        System.out.println("Fin del programa");
    }
    private static void menuPrincipal() {
        System.out.println("Menu de Opciones");
        System.out.println("================");
        System.out.println("1. Agregar Cliente");
        System.out.println("2. Listar Cientes");
        System.out.println("3. Modificar Cliente");
        System.out.println("4. Eliminar Cliente");
        System.out.println("0. Salir");
        System.out.println();
        System.out.print("Ingrese una opcion: ");
    }
    private static void agregarCliente() throws  SQLException, ParseException {
        System.out.print("Nombre: ");
        String nombre = scannerStr.nextLine();
        System.out.print("Apellido: ");
        String apellido = scannerStr.nextLine();
        System.out.print("Telefono: ");
        String telefono = scannerStr.nextLine();
        System.out.print("Correo: ");
        String correo = scannerStr.nextLine();
        System.out.print("Direccion: ");
        String direccion = scannerStr.nextLine();
        // lectura de la fecha de nacimiento en formato String
        System.out.print("Fecha de Nacimiento: ");
        String strFechaNacimiento = scannerStr.nextLine();
        // Conversión de fecha de String a Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNacimiento = sdf.parse(strFechaNacimiento);

        //System.out.println("La Fecha formato fecha " + fechaNacimiento);

        // Instanciación del cliente con la fecha convertida
        Cliente cliente = new Cliente(nombre, apellido, telefono, correo, direccion,fechaNacimiento);
        // Instanciación del cliente con la fecha convertida
        GestorClientes gestor = new GestorClientes();
        gestor.alta(cliente); // Llamada al método alta del gestor para agregar el alumno

        System.out.println("Alumno agregado exitosamente.");
    }
    private static void listarCliente() throws SQLException {
        GestorClientes gestor = new GestorClientes();
        List<Cliente> clientes = gestor.listar(); // Llamada al método listar del gestor para obtener la lista de clientes

        // Formato de cabecera
        System.out.printf("%-4s %-20s %-20s %-15s %-35s %-35s %s\n",
                "ID", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección", "Fecha Nac.");

        for (Cliente cliente : clientes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fechaNacFormateada = (cliente.getFechaNacimiento() != null) ? sdf.format(cliente.getFechaNacimiento()) : "N/A";

            System.out.printf("%-4d %-20s %-20s %-15s %-35s %-35s %s\n",
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getTelefono(),
                    cliente.getCorreo(),
                    cliente.getDireccion(),
                    fechaNacFormateada);
        }
    }
    private static void editarCliente() throws SQLException, ParseException {
        System.out.print("Ingrese el ID del cliente a modificar: ");
        int id = scannerNum.nextInt();
        GestorClientes gestor = new GestorClientes();
        Cliente cliente = gestor.buscar(id); // Llamada al método buscar del gestor para obtener el cliente a modificar

        if (cliente != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fechaNacFormateada = (cliente.getFechaNacimiento() != null) ? sdf.format(cliente.getFechaNacimiento()) : "N/A";
            System.out.println("Fecha de Nacimiento: " + fechaNacFormateada);

            System.out.println("Ingrese los nuevos datos del cliente:");
            System.out.print("Nombre: ("+cliente.getNombre()+") : ");
            String nombre = scannerStr.nextLine();
            System.out.print("Apellido: ("+cliente.getApellido()+") : ");
            String apellido = scannerStr.nextLine();
            System.out.print("Telefono: ("+cliente.getTelefono()+") : ");
            String telefono = scannerStr.nextLine();
            System.out.print("Correo: ("+cliente.getCorreo()+") : ");
            String correo = scannerStr.nextLine();
            System.out.print("Direccion: ("+cliente.getDireccion()+") : ");
            String direccion = scannerStr.nextLine();
            System.out.print("Fecha de Nacimiento: ("+fechaNacFormateada+") : ");
            String strFechaNacimiento = scannerStr.nextLine();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf2.parse(strFechaNacimiento);

            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setDireccion(direccion);
            cliente.setFechaNacimiento(fechaNacimiento);

            gestor.modificar(cliente); // Llamada al método modificar del gestor para actualizar el cliente

            System.out.println("Cliente modificado exitosamente.");
        } else {
            System.out.println("No se encontró el cliente con el ID ingresado.");
        }
    }
    private static void eliminarCliente() throws SQLException {
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        int id = scannerNum.nextInt();
        GestorClientes gestor = new GestorClientes();
        Cliente cliente = gestor.buscar(id); // Llamada al método buscar del gestor para obtener el cliente a eliminar

        if (cliente != null) {
            gestor.eliminar(cliente.getId()); // Llamada al método baja del gestor para eliminar el cliente
            System.out.println("Cliente eliminado exitosamente.");
        } else {
            System.out.println("No se encontró el cliente con el ID ingresado.");
        }
    }
}
