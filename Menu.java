import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Metodos m = new Metodos();
        
        Pacientes[] matriz = m.inicializarPacientes();
        Stack<Pacientes> pendientes = new Stack<>();
        Queue<Pacientes> atendidos = new LinkedList<>();
        Queue<Pacientes> cancelados = new LinkedList<>();
        Queue<Pacientes> prioritarios = new LinkedList<>();
        ArrayList<String> historial = new ArrayList<>();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\nBienvenido al sistema de atención de la clínica");
            System.out.println("Que desea realizar");
            System.out.println("1) Enviar paciente a la fila de atención");
            System.out.println("2) Atender siguiente paciente");
            System.out.println("3) Cancelar cita de un paciente");
            System.out.println("4) Cambiar servicio de un paciente");
            System.out.println("5) Marcar paciente como prioritario");
            System.out.println("6) Retirar paciente de la atención");
            System.out.println("7) Volver a solicitar atención");
            System.out.println("8) Mostrar pacientes registrados inicialmente");
            System.out.println("9) Mostrar pacientes pendientes");
            System.out.println("10) Mostrar pacientes atendidos");
            System.out.println("11) Mostrar pacientes cancelados");
            System.out.println("12) Mostrar pacientes prioritarios");
            System.out.println("13) Mostrar historial de operaciones");
            System.out.println("14) Salir");

            int opt = m.validarEntero(sc);

            switch (opt) {
                case 1:
                    pendientes = m.enviarAFila(matriz, pendientes, sc, historial);
                    break;
                case 2:
                    atendidos = m.atenderPaciente(pendientes, prioritarios, atendidos, historial);
                    break;
                case 3:
                    cancelados = m.cancelarCita(pendientes, prioritarios, cancelados, sc, historial);
                    break;
                case 4:
                    m.cambiarServicio(matriz, sc, historial);
                    break;
                case 5:
                    prioritarios = m.marcarPrioritario(pendientes, prioritarios, sc, historial);
                    break;
                case 6:
                    m.retirarAtencion(pendientes, prioritarios, sc, historial);
                    break;
                case 7:
                    pendientes = m.volverASolicitar(matriz, pendientes, sc, historial);
                    break;
                case 8:
                    m.mostrarRegistrados(matriz);
                    break;
                case 9:
                    m.mostrarPendientes(pendientes);
                    break;
                case 10:
                    m.mostrarAtendidos(atendidos);
                    break;
                case 11:
                    m.mostrarCancelados(cancelados);
                    break;
                case 12:
                    m.mostrarPrioritarios(prioritarios);
                    break;
                case 13:
                    m.mostrarHistorial(historial);
                    break;
                case 14:
                    System.out.println("Hasta luego");
                    continuar = false;
                    break;
                default:
                    System.out.println("Esta opción no existe");
                    break;
            }
        }
        sc.close();
    }
}