package net.ashald.envfile.platform.ui.table;

import com.intellij.ui.BooleanTableCellRenderer;
import com.intellij.util.ui.ColumnInfo;
import net.ashald.envfile.platform.EnvFileEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class EnvFileIsActiveColumnInfo extends ColumnInfo<EnvFileEntry, Boolean> {
    public EnvFileIsActiveColumnInfo() {
        super("Enabled");
    }

    @Nullable
    @Override
    public Boolean valueOf(EnvFileEntry envFileEntry) { return envFileEntry.isEnabled(); }

    @Override
    public Class getColumnClass() {
        return Boolean.class;
    }

    @Override
    public void setValue(EnvFileEntry element, Boolean checked) {
        element.setEnable(checked);
    }

    @Override
    public boolean isCellEditable(EnvFileEntry envFileEntry) {
        return true;
    }

    @Nullable
    @Override
    public TableCellRenderer getRenderer(EnvFileEntry envFileEntry) {
        return new BooleanTableCellRenderer() {
            @NotNull
            @Override
            public Component getTableCellRendererComponent(@NotNull JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {
                final Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(table.getBackground()); // Hide selection
                return renderer;
            }
        };
    }
}
