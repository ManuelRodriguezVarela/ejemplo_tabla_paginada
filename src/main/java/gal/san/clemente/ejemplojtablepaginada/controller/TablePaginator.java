package gal.san.clemente.ejemplojtablepaginada.controller;

import gal.san.clemente.ejemplojtablepaginada.utils.IPagination;
import gal.san.clemente.ejemplojtablepaginada.utils.ModelTable;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;

public class TablePaginator<T> {
    
    private JTable table;
    private IPagination<T> paginationData;
    private int[] arrayPermitRow;
    private int permitRow;
    private ModelTable<T> modelTable;
    private int actualPage = 1;
    
    private JComboBox<Integer> comboboxPermitFiles;
    private JPanel jPanelPaginationButton;
    private int limitPaginatorButton = 9;

    public TablePaginator(JTable table, IPagination<T> paginationData, int[] arrayPermitRow, int permitRow) {
        this.table = table;
        this.paginationData = paginationData;
        this.arrayPermitRow = arrayPermitRow;
        this.permitRow = permitRow;
        init();
    }
    
    private void init() {
        initModelTable();
        paginator();
    }
    
    private void initModelTable() {
        try {
            this.modelTable = (ModelTable<T>) this.table.getModel();
        } catch (Exception e) {
            System.out.println("Error método " + e.getMessage());
        }
    }
    
    private void addPaginatorButtons(JPanel panelFather, int numberPage) {
        JToggleButton toggleButton = new JToggleButton(Integer.toString(numberPage));
        
        toggleButton.setMargin(new Insets(1, 3, 1, 3));
        
        panelFather.add(toggleButton);
        
        if(numberPage == actualPage) {
            toggleButton.setSelected(true);
        }
        
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualPage = Integer.parseInt(ae.getActionCommand());
                paginator();
            }
        });
    }
    
    private void addRagePaginatorButtons(JPanel panelFather, int theBegining, int theEnd) {
        int init = theBegining;
        for(theBegining=init; theBegining <= theEnd; theBegining++) {
            addPaginatorButtons(panelFather, theBegining);
        }
    }
    
    public void updatePageButtons() {
        jPanelPaginationButton.removeAll();
        int totalRows = paginationData.getTotalRowCount();
        int pages = (int) Math.ceil((double) totalRows / permitRow);
        
        if(pages > limitPaginatorButton) {
            addPaginatorButtons(jPanelPaginationButton, 1);
                
            if(actualPage <= (limitPaginatorButton + 1 / 2)) {
                addRagePaginatorButtons(jPanelPaginationButton, 2, limitPaginatorButton - 2);
                jPanelPaginationButton.add(createHangingPoints());
                addPaginatorButtons(jPanelPaginationButton, pages);
            } else if (actualPage > (pages - ((limitPaginatorButton + 1)/2))) {
                jPanelPaginationButton.add((createHangingPoints()));
                addRagePaginatorButtons(jPanelPaginationButton, pages - limitPaginatorButton + 3, pages);
            } else {
                jPanelPaginationButton.add(createHangingPoints());
                int theBegining = actualPage - (limitPaginatorButton - 4)/2;
                int theEnd = theBegining + limitPaginatorButton - 5;
                addRagePaginatorButtons(jPanelPaginationButton, theBegining, theEnd);
                jPanelPaginationButton.add(createHangingPoints());
                addPaginatorButtons(jPanelPaginationButton, pages);
            }       
        } else {
            addRagePaginatorButtons(jPanelPaginationButton, 1, pages);
        }
        jPanelPaginationButton.getParent().validate();
        jPanelPaginationButton.getParent().repaint();
    }
    
    private JLabel createHangingPoints() {
        return new JLabel("...");
    }
    
    private void paginator() {
        int beginning = (actualPage - 1) * permitRow;
        int end = beginning + permitRow;
        if(end > paginationData.getTotalRowCount()) {
            end = paginationData.getTotalRowCount();
        }
        List<T> lista = paginationData.getRows(beginning, end);
        modelTable.setLista(lista);
        modelTable.fireTableDataChanged();
    }
    
    public void createListPermitRows(JPanel panelPaginator) {
        jPanelPaginationButton = new JPanel(new GridLayout(1, limitPaginatorButton, 3, 3));
        panelPaginator.add(jPanelPaginationButton);
        if(arrayPermitRow != null) {
            Integer array[] = new Integer[arrayPermitRow.length];
            for(int i = 0; i < arrayPermitRow.length; i++) {
                array[i] = arrayPermitRow[i];
            }
            comboboxPermitFiles = new JComboBox<>(array);
            //crea espacion entre paginador y combobox
            panelPaginator.add(Box.createHorizontalStrut(15));
            panelPaginator.add(new JLabel("Número de filas"));
            panelPaginator.add(comboboxPermitFiles);
        }
    }  
    
    public void eventCombobox(JComboBox<Integer> pageComboBox) {
        int beginningPageRow = ((actualPage - 1) * permitRow) + 1;
        permitRow = (Integer) pageComboBox.getSelectedItem();
        actualPage = ((beginningPageRow - 1)/permitRow) + 1;
        paginator();
    }

    public JComboBox<Integer> getComboboxPermitFiles() {
        return comboboxPermitFiles;
    }

    public void setComboboxPermitFiles(JComboBox<Integer> comboboxPermitFiles) {
        this.comboboxPermitFiles = comboboxPermitFiles;
    }
    
}
