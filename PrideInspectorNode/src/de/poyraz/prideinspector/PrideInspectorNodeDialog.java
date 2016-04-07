package de.poyraz.prideinspector;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;

/**
 * <code>NodeDialog</code> for the "PrideInspector" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Enes Poyraz
 */
public class PrideInspectorNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring PrideInspector node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected PrideInspectorNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentNumber(
                new SettingsModelIntegerBounded(
                    PrideInspectorNodeModel.CFGKEY_COUNT,
                    PrideInspectorNodeModel.DEFAULT_COUNT,
                    Integer.MIN_VALUE, Integer.MAX_VALUE),
                    "Counter:", /*step*/ 1, /*componentwidth*/ 5));
                    
    }
}

