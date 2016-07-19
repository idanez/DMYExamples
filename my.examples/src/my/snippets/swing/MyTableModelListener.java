package my.snippets.swing;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
 
class MyTableModelListener implements TableModelListener {
  JTable table;

  public static void main(String[] argv) throws Exception {

	    JTable table = new JTable();
	    table.getModel().addTableModelListener(new MyTableModelListener(table));
	  }
  
  MyTableModelListener(JTable table) {
    this.table = table;
  }

  public void tableChanged(TableModelEvent e) {
    int firstRow = e.getFirstRow();
    int lastRow = e.getLastRow();
    int index = e.getColumn();

    switch (e.getType()) {
    case TableModelEvent.INSERT:
      for (int i = firstRow; i <= lastRow; i++) {
        System.out.println(i);
      }
      break;
    case TableModelEvent.UPDATE:
      if (firstRow == TableModelEvent.HEADER_ROW) {
        if (index == TableModelEvent.ALL_COLUMNS) {
          System.out.println("A column was added");
        } else {
          System.out.println(index + "in header changed");
        }
      } else {
        for (int i = firstRow; i <= lastRow; i++) {
          if (index == TableModelEvent.ALL_COLUMNS) {
            System.out.println("All columns have changed");
          } else {
            System.out.println(index);
          }
        }
      }
      break;
    case TableModelEvent.DELETE:
      for (int i = firstRow; i <= lastRow; i++) {
        System.out.println(i);
      }
      break;
    }
  }
}
