package gal.san.clemente.ejemplojtablepaginada.controller;

import gal.san.clemente.ejemplojtablepaginada.model.Country;
import gal.san.clemente.ejemplojtablepaginada.model.CountryDAO;
import gal.san.clemente.ejemplojtablepaginada.utils.IPagination;
import gal.san.clemente.ejemplojtablepaginada.view.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ControllerCountry implements ActionListener, TableModelListener {
    private final Principal view;
    private final CountryDAO model;
    
    private final TablePaginator<Country> paginadorDeTabla;    
    
    /**
     * Creamos un metodo para asignar eventos los cuales serán de tipo ActionListener y TableModelListener.
     */
    public final void events(){
        view.pageJComboBox.addActionListener(this);
        view.tablaPais.getModel().addTableModelListener(this);
    }
    
    /**
     * Método constructor de la clase parametrizado.
     * @param view pasamos como argumento un objeto de tipo Principal el cual hará referencia a nuestra Interfaz gráfica.
     */
    public ControllerCountry(Principal view){
        
        this.view = view;
        model = new CountryDAO();
        
        //Creamos nuestro proveedor de datos de Paginación el cual hará posible que podamos paginar los registros agregados al JTable
        IPagination<Country> proveedorDeDatos = crearProveedorDeDatos();                  
        
        //Creamos un objeto de la clase PaginadorDeTabla, la cual nos servirá para decorar y automatizar el proceso
        // de paginación.
        paginadorDeTabla = new TablePaginator(this.view.tablaPais, proveedorDeDatos, new int[]{5, 10, 20, 50, 75, 100}, 10);
        
        //Incializamos el proceso de decoración creando un JCombobox el cual mostrara el numero de filas permitidas
        // a mostrar en el JTable
        paginadorDeTabla.createListPermitRows(view.paginationPanel);
        
        //asignamos la información que tiene dicho JComboBox Generado a nuestro JComboBox ya existente en nuestra ventana pricipal.
        view.pageJComboBox = paginadorDeTabla.getComboboxPermitFiles();
       
        events();
        
        //Seleccionamos un item de la lista desplegable.
        view.pageJComboBox.setSelectedItem(Integer.parseInt("10"));
        
    }
        
    /**
     * Metodo en el cual sobreescribiremos los metodos abstractos de la clase ProveedorDeDatosDePaginacion,
     * con el fin de crear un sublista la cual sera visualizada al JTable dependiendo los parametros que nosotros le indiquemos.
     */
    private IPagination<Country> crearProveedorDeDatos() {
        
        //Obtenemos el listado de registros existentes haciendo una consulta a la base de datos.
        List <Country> list = model.selectCountry();

        //Retornamos un interfaz de tipo ProveedorDeDatosDePaginacion en la cual sobreescribimos sus metodos abtractos
        //1 metodo: obtenemos el numero total de registros agregados al JTable.
        //2 metodo: obtenemos una subLista la cual será mostrada en el JTable, seria nuestra pagina actual.
        return new IPagination<Country>() {
            @Override
            public int getTotalRowCount() {
                return list.size();
            }

            @Override
            public List<Country> getRows(int startIndex, int endIndex) {
                //por consulta a bbdd
                return list.subList(startIndex, endIndex);
            }
        };
    }

    //Agregamos los metodos de tipo ActionListener y TableModelListener
    @Override
    public void actionPerformed(ActionEvent ae) {

        Object evt = ae.getSource();
           
        paginadorDeTabla.eventCombobox(view.pageJComboBox);
        
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        paginadorDeTabla.updatePageButtons();
    }
}
