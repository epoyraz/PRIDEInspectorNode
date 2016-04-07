package de.poyraz.prideinspector;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "PrideInspector" Node.
 * 
 *
 * @author Enes Poyraz
 */
public class PrideInspectorNodeFactory 
        extends NodeFactory<PrideInspectorNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PrideInspectorNodeModel createNodeModel() {
        return new PrideInspectorNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<PrideInspectorNodeModel> createNodeView(final int viewIndex,
            final PrideInspectorNodeModel nodeModel) {
        return new PrideInspectorNodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new PrideInspectorNodeDialog();
    }

}

