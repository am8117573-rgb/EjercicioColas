import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Metodos {
    public Pacientes[] inicializarPacientes() {
        Pacientes[] matriz = new Pacientes[5];
        matriz[0] = new Pacientes (101, "Ana", 32, "Medicina", "Pendiente");
        matriz[1] = new Pacientes (102, "Carlos", 67, "Medicina", "Pendiente");
        matriz[2] = new Pacientes (103, "Laura", 25, "Odontologia", "Pendiente");
        matriz[3] = new Pacientes (104, "Pedro", 71, "Medicina", "Pendiente");
        matriz[4] = new Pacientes (105, "Marta", 45, "Odontologia", "Pendiente");
        return matriz;
    }

    public Pacientes buscarPaciente(Pacientes[] matriz, int id) {
        for (Pacientes p : matriz) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private void registrarHistorial(ArrayList<String> historial, String accion) {
        historial.add(accion);
    }

    public Stack<Pacientes> enviarAFila(Pacientes[] matriz, Stack<Pacientes> pendientes, Scanner sc,
            ArrayList<String> historial) {
        System.out.println("Ingrese el id del paciente a enviar a la fila: ");
        int id = validarEntero(sc);
        Pacientes p = buscarPaciente(matriz, id);

        if (p == null) {
            System.out.println("No existe un paciente con ese id");
        } else if (p.getEstado().equals("Pendiente") || p.getEstado().equals("Cancelado")
                || p.getEstado().equals("Retirado")) {
            p.setEstado("En espera");
            pendientes.push(p);
            registrarHistorial(historial, "Paciente " + p.getNombre() + " (id " + id + ") enviado a la fila de atención");
            System.out.println("Paciente enviado a la fila de atención");
        } else {
            System.out.println("El paciente no se puede enviar a la fila, su estado actual es: " + p.getEstado());
        }
        return pendientes;
    }


    public Queue<Pacientes> atenderPaciente(Stack<Pacientes> pendientes, Queue<Pacientes> prioritarios,
            Queue<Pacientes> atendidos, ArrayList<String> historial) {
        Pacientes p = null;

        if (!prioritarios.isEmpty()) {
            p = prioritarios.poll();
        } else if (!pendientes.isEmpty()) {
            p = pendientes.pop();
        }

        if (p == null) {
            System.out.println("No hay pacientes pendientes ni prioritarios por atender");
        } else {
            p.setEstado("Atendido");
            atendidos.offer(p);
            registrarHistorial(historial, "Paciente " + p.getNombre() + " (id " + p.getId() + ") atendido");
            System.out.println("Se atendió a: " + p);
        }
        return atendidos;
    }


    public Queue<Pacientes> cancelarCita(Stack<Pacientes> pendientes, Queue<Pacientes> prioritarios,
            Queue<Pacientes> cancelados, Scanner sc, ArrayList<String> historial) {
        System.out.println("Ingrese el id del paciente a cancelar: ");
        int id = validarEntero(sc);

        Pacientes p = removerDePila(pendientes, id);
        if (p == null) {
            p = removerDeCola(prioritarios, id);
        }

        if (p == null) {
            System.out.println("El paciente no está en la fila de pendientes ni en prioritarios");
        } else {
            p.setEstado("Cancelado");
            cancelados.offer(p);
            registrarHistorial(historial, "Paciente " + p.getNombre() + " (id " + id + ") canceló su cita");
            System.out.println("Cita cancelada correctamente");
        }
        return cancelados;
    }


    public void cambiarServicio(Pacientes[] matriz, Scanner sc, ArrayList<String> historial) {
        System.out.println("Ingrese el id del paciente: ");
        int id = validarEntero(sc);
        sc.nextLine();
        Pacientes p = buscarPaciente(matriz, id);

        if (p == null) {
            System.out.println("No existe un paciente con ese id");
        } else {
            String anterior = p.getServicio();
            System.out.println("Ingrese el nuevo servicio: ");
            String nuevo = sc.nextLine();
            p.setServicio(nuevo);
            registrarHistorial(historial, "Paciente " + p.getNombre() + " (id " + id + ") cambió de servicio: "
                    + anterior + " -> " + nuevo);
            System.out.println("Servicio actualizado correctamente");
        }
    }

    public Queue<Pacientes> marcarPrioritario(Stack<Pacientes> pendientes, Queue<Pacientes> prioritarios, Scanner sc,
            ArrayList<String> historial) {
        System.out.println("Ingrese el id del paciente a marcar como prioritario: ");
        int id = validarEntero(sc);

        Pacientes p = removerDePila(pendientes, id);
        if (p == null) {
            System.out.println("El paciente no está en la fila de pendientes");
        } else {
            p.setEstado("Prioritario");
            prioritarios.offer(p);
            registrarHistorial(historial, "Paciente " + p.getNombre() + " (id " + id + ") marcado como prioritario");
            System.out.println("Paciente marcado como prioritario");
        }
        return prioritarios;
    }

    
    public void retirarAtencion(Stack<Pacientes> pendientes, Queue<Pacientes> prioritarios, Scanner sc,
            ArrayList<String> historial) {
        System.out.println("Ingrese el id del paciente a retirar de la atención: ");
        int id = validarEntero(sc);

        Pacientes p = removerDePila(pendientes, id);
        if (p == null) {
            p = removerDeCola(prioritarios, id);
        }

        if (p == null) {
            System.out.println("El paciente no está en la fila de pendientes ni en prioritarios");
        } else {
            p.setEstado("Retirado");
            registrarHistorial(historial, "Paciente " + p.getNombre() + " (id " + id + ") retirado de la atención");
            System.out.println("Paciente retirado de la atención");
        }
    }

    

    public Stack<Pacientes> volverASolicitar(Pacientes[] matriz, Stack<Pacientes> pendientes, Scanner sc,
            ArrayList<String> historial) {
        return enviarAFila(matriz, pendientes, sc, historial);
    }


    private Pacientes removerDePila(Stack<Pacientes> pila, int id) {
        Stack<Pacientes> aux = new Stack<>();
        Pacientes encontrado = null;

        while (!pila.isEmpty()) {
            Pacientes actual = pila.pop();
            if (actual.getId() == id && encontrado == null) {
                encontrado = actual;
            } else {
                aux.push(actual);
            }
        }
        while (!aux.isEmpty()) {
            pila.push(aux.pop());
        }
        return encontrado;
    }

    private Pacientes removerDeCola(Queue<Pacientes> cola, int id) {
        Queue<Pacientes> aux = new LinkedList<>();
        Pacientes encontrado = null;

        while (!cola.isEmpty()) {
            Pacientes actual = cola.poll();
            if (actual.getId() == id && encontrado == null) {
                encontrado = actual;
            } else {
                aux.offer(actual);
            }
        }
        while (!aux.isEmpty()) {
            cola.offer(aux.poll());
        }
        return encontrado;
    }

    public void mostrarRegistrados(Pacientes[] matriz) {
        System.out.println("Pacientes registrados inicialmente:");
        for (Pacientes p : matriz) {
            System.out.println(p);
        }
    }

    public void mostrarPendientes(Stack<Pacientes> pendientes) {
        if (pendientes.isEmpty()) {
            System.out.println("No hay pacientes pendientes");
        } else {
            System.out.println("Pacientes pendientes (en orden de llegada):");
            for (Pacientes p : pendientes) {
                System.out.println(p);
            }
        }
    }

    public void mostrarAtendidos(Queue<Pacientes> atendidos) {
        if (atendidos.isEmpty()) {
            System.out.println("No hay pacientes atendidos");
        } else {
            System.out.println("Pacientes atendidos:");
            for (Pacientes p : atendidos) {
                System.out.println(p);
            }
        }
    }

    public void mostrarCancelados(Queue<Pacientes> cancelados) {
        if (cancelados.isEmpty()) {
            System.out.println("No hay pacientes cancelados");
        } else {
            System.out.println("Pacientes cancelados:");
            for (Pacientes p : cancelados) {
                System.out.println(p);
            }
        }
    }

    public void mostrarPrioritarios(Queue<Pacientes> prioritarios) {
        if (prioritarios.isEmpty()) {
            System.out.println("No hay pacientes prioritarios");
        } else {
            System.out.println("Pacientes prioritarios:");
            for (Pacientes p : prioritarios) {
                System.out.println(p);
            }
        }
    }

    public void mostrarHistorial(ArrayList<String> historial) {
        if (historial.isEmpty()) {
            System.out.println("Aún no se ha realizado ninguna operación");
        } else {
            String[] arregloHistorial = historial.toArray(new String[0]);
            System.out.println("Historial de operaciones:");
            for (int i = 0; i < arregloHistorial.length; i++) {
                System.out.println((i + 1) + ". " + arregloHistorial[i]);
            }
        }
    }

    public int validarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Por favor ingrese un valor numerico valido");
            sc.next();
        }
        return sc.nextInt();
    }
}
