package de.poyraz.prideinspector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.data.uri.IURIPortObject;
import org.knime.core.data.uri.URIContent;
import org.knime.core.data.uri.URIPortObject;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.port.PortTypeRegistry;

/**
 * This is the model implementation of PrideInspector.
 * 
 *
 * @author Enes Poyraz
 */
public class PrideInspectorNodeModel extends NodeModel {

	// the logger instance
	private static final NodeLogger logger = NodeLogger.getLogger(PrideInspectorNodeModel.class);

	/**
	 * the settings key which is used to retrieve and store the settings (from
	 * the dialog or from a settings file) (package visibility to be usable from
	 * the dialog).
	 */
	static final String CFGKEY_COUNT = "Count";

	/** initial default count value. */
	static final int DEFAULT_COUNT = 100;

	// example value: the models count variable filled from the dialog
	// and used in the models execution method. The default components of the
	// dialog work with "SettingsModels".
	private final SettingsModelIntegerBounded m_count = new SettingsModelIntegerBounded(
			PrideInspectorNodeModel.CFGKEY_COUNT, PrideInspectorNodeModel.DEFAULT_COUNT, Integer.MIN_VALUE,
			Integer.MAX_VALUE);

	/**
	 * Constructor for the node model.
	 */
	protected PrideInspectorNodeModel() {

		// TODO one incoming port and one outgoing port is assumed
		super(new PortType[] { PortTypeRegistry.getInstance().getPortType(URIPortObject.class),
				PortTypeRegistry.getInstance().getPortType(URIPortObject.class) }, new PortType[] {});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BufferedDataTable[] execute(final PortObject[] inData, final ExecutionContext exec) throws Exception {

		
		IURIPortObject mzml = (IURIPortObject) inData[0];
		List<URIContent> uris = mzml.getURIContents();
		URI relURI = uris.get(0).getURI();
		System.out.println(relURI.toString());
		
		IURIPortObject mztab = (IURIPortObject) inData[1];
		List<URIContent> uris2 = mztab.getURIContents();
		URI relURI2 = uris2.get(0).getURI();
		System.out.println(relURI2.toString());
		
		
		System.out.println("executed");
		//Process pb = new ProcessBuilder("java", "-jar", "/Users/epoyraz/Desktop/Uni/Masterarbeit/ms-software/mzml-viewer-1.6.7/jmzmlGUI-1.6.7.jar").inheritIO().start();
		//Process pb = new ProcessBuilder("java", "-jar", "jmzmlGUI-1.6.7.jar").inheritIO().start();
		String filepath = new File(PrideInspectorNodeModel.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getCanonicalPath().toString();
		String path = filepath.replace("prideinspector.jar", "jmzml/jmzmlGUI-1.6.7.jar");
		//path = URLEncoder.encode(path, "UTF-8"); 
		path = "\"" + path + "\"" ;
		logger.warn(path);
		logger.warn(System.getenv("PATH"));
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", path);
		pb.inheritIO();
		File commands = new File("/Users/epoyraz/Desktop/output/commands.txt");
		File dirOut = new File("/Users/epoyraz/Desktop/output/dirOut.txt");
		File dirErr = new File("/Users/epoyraz/Desktop/output/dirErr");

		pb.redirectInput(commands);
		pb.redirectOutput(dirOut);
		pb.redirectError(dirErr);
		Process process = pb.start();
		   // get the input stream of the process and print it
		   InputStream in = process.getErrorStream();
		   for (int i = 0; i < in.available(); i++) {
			   logger.warn("" + in.read());
		   }
		process.getInputStream();
		System.out.println("");
		System.out.println("Working Directory = " + System.getProperty("user.dir"));	
		


		
	    Runtime runtime = Runtime.getRuntime();
	    String command = "java -jar "+path;
		logger.warn(command);
	    Process p2 = runtime.exec(command);

		   InputStream in2 = p2.getErrorStream();
		   for (int i = 0; i < in2.available(); i++) {
			   logger.warn("in2: " + in2.read());
		   }
           int exitVal = p2.exitValue();
           logger.warn("Process exitValue: " + exitVal);

	    File dir1 = new File (".");
	    System.out.println ("Current dir : " + dir1.getCanonicalPath());
		// TODO do something here
		logger.info("Node Model Stub... this is not yet implemented !");

		// the data table spec of the single output table,
		// the table will have three columns:
		DataColumnSpec[] allColSpecs = new DataColumnSpec[3];
		allColSpecs[0] = new DataColumnSpecCreator("Column 0", StringCell.TYPE).createSpec();
		allColSpecs[1] = new DataColumnSpecCreator("Column 1", DoubleCell.TYPE).createSpec();
		allColSpecs[2] = new DataColumnSpecCreator("Column 2", IntCell.TYPE).createSpec();
		DataTableSpec outputSpec = new DataTableSpec(allColSpecs);
		// the execution context will provide us with storage capacity, in this
		// case a data container to which we will add rows sequentially
		// Note, this container can also handle arbitrary big data tables, it
		// will buffer to disc if necessary.
		BufferedDataContainer container = exec.createDataContainer(outputSpec);
		// let's add m_count rows to it
		for (int i = 0; i < m_count.getIntValue(); i++) {
			RowKey key = new RowKey("Row " + i);
			// the cells of the current row, the types of the cells must match
			// the column spec (see above)
			DataCell[] cells = new DataCell[3];
			cells[0] = new StringCell("String_" + i);
			cells[1] = new DoubleCell(0.5 * i);
			cells[2] = new IntCell(i);
			DataRow row = new DefaultRow(key, cells);
			container.addRowToTable(row);

			// check if the execution monitor was canceled
			exec.checkCanceled();
			exec.setProgress(i / (double) m_count.getIntValue(), "Adding row " + i);
		}
		// once we are done, we close the container and return its table
		container.close();
		BufferedDataTable out = container.getTable();
		return new BufferedDataTable[] {};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void reset() {
		// TODO Code executed on reset.
		// Models build during execute are cleared here.
		// Also data handled in load/saveInternals will be erased here.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DataTableSpec[] configure(final PortObjectSpec[] inSpecs) throws InvalidSettingsException {

		// TODO: check if user settings are available, fit to the incoming
		// table structure, and the incoming types are feasible for the node
		// to execute. If the node can execute in its current state return
		// the spec of its output data table(s) (if you can, otherwise an array
		// with null elements), or throw an exception with a useful user message

		return new DataTableSpec[] {};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {

		// TODO save user settings to the config object.

		m_count.saveSettingsTo(settings);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO load (valid) settings from the config object.
		// It can be safely assumed that the settings are valided by the
		// method below.

		m_count.loadSettingsFrom(settings);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO check if the settings could be applied to our model
		// e.g. if the count is in a certain range (which is ensured by the
		// SettingsModel).
		// Do not actually set any values of any member variables.

		m_count.validateSettings(settings);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {

		// TODO load internal data.
		// Everything handed to output ports is loaded automatically (data
		// returned by the execute method, models loaded in loadModelContent,
		// and user settings set through loadSettingsFrom - is all taken care
		// of). Load here only the other internals that need to be restored
		// (e.g. data used by the views).

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {

		// TODO save internal models.
		// Everything written to output ports is saved automatically (data
		// returned by the execute method, models saved in the saveModelContent,
		// and user settings saved through saveSettingsTo - is all taken care
		// of). Save here only the other internals that need to be preserved
		// (e.g. data used by the views).

	}

}
