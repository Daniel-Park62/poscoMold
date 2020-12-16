package mold.posco.part;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

import mold.posco.model.MoteStatus;

public class OptionYNEditingSupport extends EditingSupport 
{
    private ComboBoxCellEditor cellEditor;

    public OptionYNEditingSupport(ColumnViewer viewer) {
        super(viewer);
        cellEditor = new ComboBoxCellEditor(((TableViewer)viewer).getTable(), new String[]{"Y", "N"});
    }
    protected CellEditor getCellEditor(Object element) {
        return cellEditor;
    }
    protected boolean canEdit(Object element) {
        return true;
    }
    protected Object getValue(Object element) {
    	if (element instanceof MoteStatus) 
        return (((MoteStatus)element).getSpare() == "Y" ) ? 0 : 1 ;
    	return 0 ;
    }
    protected void setValue(Object element, Object value) 
    {
        if((element instanceof MoteStatus) && (value instanceof Integer)) {
            Integer choice = (Integer)value;
            String option = (choice == 0? "Y":"N");
            ((MoteStatus)element).setSpare( option );
            getViewer().update(element, null);
        }
    }

}
