package test1;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Prototipo1 {


    public static int pintar_menu_recoger_opcion() {//Aqu� se est� el men� donde se recoge la opci�n

        int opcion = -1;
        // se resetea la opcion
        //opcion = 0;
        Scanner teclado = new Scanner(System.in);
        // Informar de las opciones
        System.out.print(
                "-------- Menu de control de calles de Madrid --------" + "\n" +
                        "  0 - Salir" + "\n" +
                        "  1 - Todas las localidades en el sistema" + "\n" +
                        "  2 - Introducir informaci�n de las localidades" + "\n" +
                        //"  ? - Todas las calles de la localidad" + "\n" +
                        //"  ? - Introducir informaci�n de las calles" + "\n" +
                        "  3 - Eliminar informaci�n" + "\n" +
                        "  4 - Actualizar informaci�n del producto" + "\n" +
                        "  5 - Guardar informacion en archivo" + "\n" +
                        "  6 - Importar stock desde archivo"  + "\n" +

                        "Opci�n:"
        );

        // recoger la opci�n
        if (teclado.hasNextInt()) {
            opcion = teclado.nextInt();
        } else {
            teclado.next(); // Vaciar la basura (no int) del teclado...
        }

        return opcion;
    }


    public static void guardar_localidad(String[][] productos_actuales, FileWriter fichero) {
    	LocalDateTime Fecha = LocalDateTime.now(); //Esto es importante para el log que tenga la fecha y hora
    	int nproductos = -1;

    	Scanner teclado = new Scanner(System.in);

    	System.out.println("Introduzca ID Localidad"); //Se piden los productos que se van a insertar para ponerlos todos del tir�n

    	if (teclado.hasNextInt()) {
    		nproductos = teclado.nextInt();
    		if(nproductos>101){
    			System.out.println("El almacenamiento m�ximo es de 100, por lo que s�lo se guardar�n los 100 primeros productos");
    		}//Le he puesto un almacenamiento m�ximo dado que es lo m�s ajustado a la realidad, en este caso era de 100 productos pero puede ser aumentado f�cilmente.
    	}
    	else {
    		teclado.next();//Si no es un n�mero lo devuelve
    		System.out.println("Debe insertar un n�mero");
    	}


    	for (int i = 0; i < nproductos; i++) {//Aqu� te va pidiendo los datos de los productos, los cuales se van a�adiendo uno debajo del otro.
    		String[] producto = new String[4];
    		String codigo = conseguir_codigo(productos_actuales); //Te lleva m�s abajo donde se crea el c�digo aleatorio
    		producto[0] = codigo;
    		System.out.println("Datos del producto " + (i + 1)); //Te va pidiendo cada apartado, excepto el c�digo que se genera autom�ticamente
    		System.out.println("Inserte nombre de localidad:");
    		String nombre = teclado.next();
    		producto[1] = nombre;
    		System.out.println("Inserte codigo postal:");
    		String precio = teclado.next();
    		producto[2] = precio;//Curiosidad: el precio puede ser cualquier cosa porque puede que no quieras pagarlo en monedas, tambi�n se pueden hacer intercambios de cosas, nunca se sabe.
    		System.out.println("Inserte preve descripcion:");
    		String marca = teclado.next();
    		producto[3] = marca;

    		int fila = 0;
    		boolean encontrado = false;
    		while ((!encontrado) && (fila < 100)) {
    			if (productos_actuales[fila][0].equals("")) { //Aqu� comprueba los huecos que est�n ocupados y si est� ocupado lon pone en el siguiente
    				encontrado = true;
    			}
    			else {
    				fila++;
    			}
    		}

    		if (!encontrado) {
    			System.out.println("No se puede a�adir la informacion, no quedan huecos."); //Si todos los huecos est�n ocupados, dado que el almacenamiento es limitado te devuelve el producto.
    			return;  // Se podr�an a�adir m�s productos si se quitaran productos que haya (o que se vendan)
    		}
    		else {
    			productos_actuales[fila] = producto;
    			String mensaje = "Localidad " + productos_actuales[fila][1] + " con c�digo " + codigo + " a�adido con �xito.";
    			System.out.println(mensaje);
    			try {
    				fichero.write(Fecha + "  Localidad " + productos_actuales[fila][1] + " con c�digo " + codigo + " a�adido con �xito." + "\n");
    			}
    			catch (IOException e) {
    				System.out.println("Error al registrar esta acci�n en el log");
    			}
    		}

    	}
    }

    public static String conseguir_codigo(String[][] productos_actuales) {
        int numero = (int) Math.round(Math.random() * 50000); //Se genera un c�digo aleatorio del 0 al 50.000
        String codigo = Integer.toString(numero);
        int fila = 0;
        while (fila < 100) {
            if (productos_actuales[fila][0].equals(codigo)) {
                // Generamos otro n�mero diferente en el caso de que se repitiera, que es muy dif�cil dado que hay pocos productos para tantos n�meros
                numero = (int) Math.round(Math.random() * 50000);
                codigo = Integer.toString(numero); //Se convierte a string
                fila = 0;
            } else {
                fila++;
            }
        }

        return codigo;
    }

    public static void informacion_localidad(String[][] productos_actuales,  FileWriter fichero) {
        LocalDateTime Fecha = LocalDateTime.now();
        System.out.println("ID localidad          Localidad               Codigo Postal               Resto de informacion"); //En este punto no sab�a exactamente c�mo colocar
        for (int i = 0; i < 100; i++) {                                                        //unos debajo de otros por lo que opt� por poner espacios
            if (!productos_actuales[i][0].equals("")) {     //Aqu� si est� lleno el array lo muestra, sino no.
                System.out.print(productos_actuales[i][0] + "             ");
                System.out.print(productos_actuales[i][1] + "                   ");
                System.out.print(productos_actuales[i][2] + "            ");
                System.out.print(productos_actuales[i][3]);
                System.out.println("" +
                        "");
            }
        }
        try {
            fichero.write(Fecha + "  Se ha observado en el systema "+ "\n");    //Se indica en el log que se ha observado el cat�logo
        }
        catch (IOException e) {
            System.out.println("Error al registrar esta acci�n en el log");  //Esto por si hay alg�n error
        }
    }

    private static void eliminar_informacion(String[][] productos_actuales, FileWriter fichero) {
        LocalDateTime Fecha = LocalDateTime.now();
        Scanner teclado = new Scanner(System.in);
        System.out.println("Inserte el c�digo del producto que desea eliminar");
        teclado.hasNextInt();
        String buscar = teclado.nextBigInteger().toString();  //Simplemente se elimina el producto, la forma de buscarlo es por c�digo �nicamente,
        boolean encontrado = false;                           // dado que puede haber varios productos con el mismo nombre/precio/marca
        int i = 0;
        while ((i < 100) && (!encontrado)){
            if (buscar.equals(productos_actuales[i][0])) {
                encontrado = true;
            }
            else {
                i++;
            }
        }
        if (encontrado) {
            System.out.println("Se ha eliminado la localidad " + productos_actuales[i][1] + " con c�digo " + buscar); //Lo pongo aqu� porque si lo pongo despu�s
            productos_actuales[i][0] = "";                                                                           //ya no existe el nombre del producto eliminado
            productos_actuales[i][1] = "";
            productos_actuales[i][2] = "";
            productos_actuales[i][3] = "";
        }
        else {
            System.out.println("No se ha encontrado la localidad");
        }
        try {
            fichero.write(Fecha + "  Se ha eliminado la localidad en el codigo " + buscar + "\n");// Se guarda en el log
        }
        catch (IOException e) {
            System.out.println("Error al registrar esta acci�n en el log");
        }
    }

    private static void actualizar_precio(String[][] productos_actuales, FileWriter fichero) {
        LocalDateTime Fecha = LocalDateTime.now();
        Scanner teclado = new Scanner(System.in);
        System.out.println("Inserte el c�digo del producto que desea cambiar el precio");
        teclado.hasNextInt();
        String buscar = teclado.nextBigInteger().toString(); //Siempre pido el c�digo para estas cosas porque lo otro puede estar repetido
        boolean encontrado = false;
        int i = 0;
        while ((i < 100) && (!encontrado)){
            if (buscar.equals(productos_actuales[i][0])) {  //Cuando encuentra el producto se da la opci�n de poner un precio nuevo
                encontrado = true;
            }
            else {
                i++;
            }
        }
        if (encontrado) {
            System.out.println("Escriba el precio nuevo:");

            if (teclado.hasNextInt()) {

                String newprice = Integer.toString(teclado.nextInt());
                productos_actuales[i][2] = newprice;
                System.out.println("Se ha actualizado el precio del producto " + productos_actuales[i][1] + " y c�digo " + buscar);
            }
            else {
                System.out.println("No se pudo actualizar, tiene que ser un n�mero.");
            }
        }
        else {
            System.out.println("No se ha encontrado el producto");
            return;
        }
        try {
            fichero.write(Fecha + "  Se ha actualizado el precio del producto "+ productos_actuales[i][1] + " y c�digo " + buscar + "\n");
        }
        catch (IOException e) {
            System.out.println("Error al registrar esta acci�n en el log");
        }
    }

    private static void guardar_listado(String[][] productos_actuales, String ruta, FileWriter fichero_log) {
        LocalDateTime Fecha = LocalDateTime.now();
        FileWriter fichero;     //Aqu� puedes guardar el stock en un fichero
        try {
            fichero = new FileWriter(ruta);
            for(int i = 0; i< productos_actuales.length; i++) {
                if (!productos_actuales[i][0].equals("")) {//Se guarda por orden en un fichero creado anteriormente con un nombre elegido
                    fichero.write("" + productos_actuales[i][0] + ",");
                    fichero.write("" + productos_actuales[i][1] + ",");
                    fichero.write("" + productos_actuales[i][2] + ",");
                    fichero.write("" + productos_actuales[i][3]);
                        // fuera del bucle poner un write del �ltimo sin ;
                    fichero.write("\n");
                }

            }
            System.out.println("Se ha guardado con �xito");
            fichero_log.write(Fecha + "  Las localidades guardados al fichero");
            fichero.close();
        } catch (IOException e) {
            System.out.println("ERROR en el guardado del listado.");
        }
    }    // Guarda la matriz en ruta

   // Lee la matriz de ruta y la devuelve
   private static String[][] importar_stock(String[][] productos_actuales, FileWriter fichero_log) {
       Scanner teclado = new Scanner(System.in);
        System.out.println("Pon el nombre del archivo del cual quieres sacar el stock y a�adirlo a este");// Tengo que preguntar el nombre del archivo primero del que se desea sacar el stock.
        String StockAnterior = teclado.next();                                                            //El nombre tiene que ser exacto, sino no funciona
        String ruta = StockAnterior;        //Aqu� coge el nombre de la ruta que le has puesto
        try {
            FileReader fichero = new FileReader(ruta);  //Lee la ruta
            Scanner sc = new Scanner(fichero);

            int i = 0;
            String fila;
            String[] array_fila;

            while(sc.hasNextLine()) {
                fila = sc.nextLine();
                array_fila = fila.split(",");   //Se separan las columnas por comas, es decir, nombre, precoi, c�digo, marca.

                int fila_matriz = 0;
                boolean encontrado = false;
                while ((!encontrado) && (fila_matriz < 100)) {
                    if (productos_actuales[fila_matriz][0].equals("")) {  //Est� puesto de tal manera que se a�ade a los productos
                        encontrado = true;                                //que estaban ya en el stock, en el caso de que los hubiera.
                    }
                    else {
                        fila_matriz++;
                    }
                }

                if (!encontrado) {
                    System.out.println("Uno o m�s productos no se han podido a�adir porque no quedan huecos.");
                    return productos_actuales;  //Se a�aden los productos que se puedan hasta el l�mite, en este caso 100.
                }
                else {
                    productos_actuales[fila_matriz] = array_fila;
                }

                i++;
            }
            System.out.println("Se han a�adido con �xito los productos del fichero al stock.");
            LocalDateTime Fecha = LocalDateTime.now();
            fichero_log.write(Fecha + "  Se han a�adido con �xito los productos del fichero al stock." + "\n");
            fichero.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error, no se puede abrir el fichero.");
        }
        catch (IOException e) {
            System.out.println("Error de lectura del fichero.");
        }
       return productos_actuales;
    }


    public static void main(String[] args) {
        LocalDateTime fecha = LocalDateTime.now();
        //Cojo la fecha actual para crear los logs y que no haya ninguno igual.
        FileWriter fichero;
        try {
            String nombreFecha = "Log " + fecha.getMonth() + " " + fecha.getDayOfMonth() + " " + fecha.getHour() + " " + fecha.getMinute() + ".csv";
            fichero = new FileWriter(nombreFecha);
            int opcion = -1;
            String[][] listado_productos = new String[100][4];     //Aqu� se pone que tiene 4 columnas y 100 filas, este n�mero podr�a aumentar adapt�ndose al almac�n.
            for (int i = 0; i < 100; i++) {
                listado_productos[i][0] = "";
            }

            while (opcion != 0) {
                opcion = pintar_menu_recoger_opcion();

                switch (opcion) {
                    case 0:
                        fichero.close();
                        System.out.println("Hasta pronto!");
                        break;
                    case 1: // Mostrar stock productos, una lista con todos los productos con c�digo, nombre, precio y marca.
                        informacion_localidad(listado_productos, fichero);
                        break;
                    case 2: //Aqu� se sube el producto manualmente.
                        guardar_localidad(listado_productos, fichero);
                        break;
                    case 3: // Aqu� se elimina un producto a�adido anteriormente o importado de los archivos.
                        eliminar_informacion(listado_productos, fichero);
                        break;
                    case 4: //Se actualiza el precio de un producto.
                        actualizar_precio(listado_productos, fichero);
                        break;
                    case 5: //Guardar listado en un fichero, luego se puede cerrar el programa y si se quisiera m�s tarde volver a abrir.
                        Scanner teclado = new Scanner(System.in);// Tengo que preguntar el nombre del archivo primero al que se desea guardar.
                        System.out.println("�Con qu� nombre desear�a llamar el archivo?");
                        String nombrearchivo = teclado.next();
                        guardar_listado(listado_productos,  nombrearchivo + ".csv", fichero);
                        break;
                    case 6: //Sacar stock desde archivo.
                        listado_productos = importar_stock(listado_productos,fichero);
                        break;
                    default:
                        System.out.println("�Opci�n incorrecta! Ingrese un n�mero de las opciones"); //En el caso de que no cojas una opci�n te pide que ingreses otro n�mero v�lido
                }
            }
        }
        catch (IOException e) {
            System.out.println("ERROR en el guardado del listado.");
        }

    }
}
