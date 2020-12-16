package mold.posco.part;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.wb.swt.SWTResourceManager;

import mold.posco.model.ChartData;
import mold.posco.model.FindMoteInfo;
import mold.posco.model.MoteStatus;
import mold.posco.model.PeakTrend;

public class MiniChart {
	private Chart chart ;
	
	private static final double[] yS1 = { 30,31,32,34,35,36,37,38,37,35,34,33,32,31,30,29,20,33};
	String[] slist,  slistA;
	private ILineSeries lineSeries  ; 
	private Color[] colors = {
			SWTResourceManager.getColor(SWT.COLOR_BLUE),
			SWTResourceManager.getColor(SWT.COLOR_RED),
			SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN),
			SWTResourceManager.getColor(SWT.COLOR_GREEN),
			SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA),
			SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW),
			SWTResourceManager.getColor(SWT.COLOR_GREEN),
	};
	
	private int axisId ;
	final Color BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	final Color BLUE = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
	final Color GREEN = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
	final Color CYAN = Display.getDefault().getSystemColor(SWT.COLOR_DARK_CYAN);
	
	MoteStatus motestatus ;
	FindMoteInfo findMoteinfo = new FindMoteInfo();
//	ArrayList<MoteHist> arrayinfo ;
	ArrayList<ChartData> arrayinfo ;

	/**
     * Create the composite.
     *
     * @param parent
     * @param style
     * @wbp.parser.entryPoint
     */

	int sensorno = 0;
	Label lblDate, lblTime,  lblfrom ,lblfromd, lblto, lbltod ;
	
	final Label lblstat, lbltit, lblmaxv, lblminv ;
	int px ;
	double py ;
	final Font font = SWTResourceManager.getFont("Calibri", 20, SWT.BOLD);
	final Font font2 = SWTResourceManager.getFont("MS Gothic", 20, SWT.NORMAL);
	final Color colact = SWTResourceManager.getColor(49,136,248) ;
	final Color colinact = SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY) ;
	final Color collow = SWTResourceManager.getColor(245,174,0) ;
	final Color colout = SWTResourceManager.getColor(SWT.COLOR_RED) ;
    public MiniChart(Composite parent, int style, MoteStatus mote) {

    	this.motestatus = mote ;
    	final SashForm sash = new SashForm(parent, SWT.HORIZONTAL) ;
    	sash.setSashWidth(3);
//    	sash.setBackground(SWTResourceManager.getColor(250, 250, 252));
    	Composite comp1 = new Composite(sash, SWT.LINE_DASH);
    	comp1.setLayout(new GridLayout(1, false));
    	comp1.setBackground(SWTResourceManager.getColor(250, 250, 252));
		
    	lblstat = new Label(comp1, SWT.NONE) ;
    	lblstat.setText("    ");
    	lblstat.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
    	lblstat.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.BOLD));
    	lblstat.setBackground(colact);
    	
    	lbltit = new Label(comp1, SWT.NONE) ;
//    	lbltit.setText(motestatus.getLoc());
    	lbltit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    	lbltit.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
    	lbltit.setBackground(comp1.getBackground());
    	lbltit.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
    	
    	Label l0 = new Label(comp1,SWT.SEPARATOR | SWT.HORIZONTAL );
		l0.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
    	final Label btnzero = new Label(comp1, SWT.NONE) ;
    	btnzero.setText("  Zeroing  ");

    	btnzero.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    	btnzero.setFont(SWTResourceManager.getFont("���� ���", 14, SWT.BOLD));
//    	btnzero.setBackground(SWTResourceManager.getColor(230, 230, 240));
    	btnzero.setImage(SWTResourceManager.getImage("images/btnZero.png"));
    	
    	btnzero.setCursor(SWTResourceManager.getCursor(  SWT.CURSOR_HAND));
    	
    	btnzero.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				AppMain.sendZero(motestatus.getMac());
			}
		});
    	
		chart = new Chart(sash, SWT.BORDER);
    	Composite comp2 = new Composite(sash, SWT.NONE);
    	comp2.setBackground(SWTResourceManager.getColor(250, 250, 252));
    	comp2.setLayout(new GridLayout(1, false));

    	Label lblmaxt = new Label(comp2, SWT.NONE);
    	lblmaxt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    	lblmaxt.setFont(font);
    	lblmaxt.setText("MAX");
    	lblmaxt.setBackground(comp2.getBackground());
    	
    	lblmaxv = new Label(comp2, SWT.NONE);
    	lblmaxv.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    	lblmaxv.setFont(font2);
    	lblmaxv.setText("" + motestatus.getMaxval());
    	lblmaxv.setBackground(comp2.getBackground());
    	lblmaxv.setForeground(SWTResourceManager.getColor(SWT.COLOR_MAGENTA));
    	
    	Label l1 = new Label(comp2,SWT.SEPARATOR | SWT.HORIZONTAL);
    	l1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

    	Label lblmint = new Label(comp2, SWT.NONE);
    	lblmint.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    	lblmint.setFont(font);
    	lblmint.setText("MIN");
    	lblmint.setBackground(comp2.getBackground());

    	lblminv = new Label(comp2, SWT.NONE);
    	lblminv.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    	lblminv.setFont(font2);
    	lblminv.setText("" + motestatus.getMinval());
    	lblminv.setBackground(comp2.getBackground());
    	lblminv.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
    	
    	sash.setWeights(new int[] {5,90,5});
    	sash.setSashWidth(0);

		chart.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		chart.setCursor(SWTResourceManager.getCursor( SWT.CURSOR_CROSS));
		
		// set titles
		chart.getTitle().setText("S0");
		chart.getTitle().setFont(SWTResourceManager.getFont("Calibri", 14, SWT.BOLD));

		// create second Y axis
//		axisId = chart.getAxisSet().createYAxis();
		axisId = 0 ;
		
		// set the properties of second Y axis
//		IAxis yAxis2 = chart.getAxisSet().getYAxis(axisId);
//		yAxis2.setPosition(Position.Secondary);

		// create line series
		lineSeries = (ILineSeries)chart.getSeriesSet().createSeries(SeriesType.LINE, "S0" );
		lineSeries.setLineStyle(LineStyle.SOLID);
		lineSeries.setYAxisId(axisId);
		lineSeries.setSymbolColor(colors[5]);
		lineSeries.setLineColor(colors[5]);
		lineSeries.setYSeries(yS1);
		
		chart.getLegend().setVisible(false);
		chart.getLegend().setPosition(SWT.TOP);
		chart.getLegend().setFont(SWTResourceManager.getFont("Calibri", 14, SWT.NORMAL));
		// adjust the axis range
//		chart.getAxisSet().adjustRange();
//		sash1.setWeights(new int[] {5,95});
		
		chart.setFocus();
		final IAxis xAxis = chart.getAxisSet().getXAxis(0);
		final IAxis yAxis = chart.getAxisSet().getYAxis(0);
//		xAxis.getTick().setVisible(false);
//		xAxis.getTick().setForeground(BLACK);
//		xAxis.getTick().setFont(new Font(Display.getCurrent(),"Calibri", 12, SWT.BOLD));
//		xAxis.getTitle().setForeground(BLACK);
//		yAxis.getTick().setVisible(false);
		
		xAxis.getTitle().setVisible(false);
		yAxis.getTitle().setVisible(false);
		yAxis.getTitle().setForeground(BLACK);
		yAxis.getTitle().setText("Strain Value");

		yAxis.getTick().setForeground(BLACK);
		yAxis.getTick().setFont(SWTResourceManager.getFont("MS Gothic", 10, SWT.BOLD));
		yAxis.getTick().setFormat(new DecimalFormat("##,###"));

		chart.setLayoutData(new FillLayout());
    	xAxis.enableCategory(true) ;
    	
		// adjust the axis range
		try {

			xAxis.adjustRange();
			yAxis.adjustRange();
		} catch (SWTException e) {
			// TODO: handle exception
		}
		
		chart.redraw();
		
		chart.getPlotArea().addMouseMoveListener(new MouseMoveListener() {

			public void mouseMove(MouseEvent e) {

				px = (int)xAxis.getDataCoordinate(e.x);
				py = yAxis.getDataCoordinate(e.y);
				if (px >= 0  && px < catdate.length )
				try {
					chart.getPlotArea().setToolTipText(" " + catdate[px] + "\n Strain:" + String.format("%,2.0f", py));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});
		

    }
    
	String[] catdate = {""};
	private DateFormat datefmt = new SimpleDateFormat("HH:mm:ss");
	
	public void setMoteStatus(MoteStatus motes) {
		this.motestatus = motes ;
	}

	public void setChartData() {

		if (motestatus == null) return ;
		EntityManager em = AppMain.emf.createEntityManager();
		motestatus = em.createNamedQuery("MoteStatus.findById", MoteStatus.class)
				.setParameter("seq", motestatus.getSeq())
				.getSingleResult() ;
		
		Color col = colact;
		if  (motestatus.getBatt() < 3.5 && motestatus.getAct() == 2) col = collow ;
		if  (motestatus.getObcnt() > 0) col = colout ;
		if  (motestatus.getAct() != 2) col = colinact ;
		lblstat.setBackground(col);
		lblminv.setText("" + motestatus.getMinval());
		lblmaxv.setText("" + motestatus.getMaxval());
		lblminv.requestLayout();
		lblmaxv.requestLayout();
		
		int sno = motestatus.getSeq() ;
		List<PeakTrend> arrayinfo = findMoteinfo.getPeakTrend(sno) ;
    	try {
   			lineSeries.setVisible(false);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
    	catdate = null ;
    	catdate = arrayinfo.stream().map(a -> datefmt.format(a.getTm()) )
    							.distinct().toArray(String[]::new)  ;

    	double[] ydv  =  arrayinfo.stream()
				.map( a -> a.getVal() ).mapToDouble(a -> a).toArray() ;


    	chart.getTitle().setText("S" + sno);
		lineSeries.setYSeries(ydv);
//		lineSeries.setSymbolColor(GREEN);
		lineSeries.setYAxisId(axisId);
		lineSeries.setSymbolType(PlotSymbolType.CIRCLE);
		lineSeries.setSymbolSize(2);
		lineSeries.setLineStyle(LineStyle.SOLID);
		lineSeries.setAntialias(SWT.ON);
		lineSeries.setVisible(true);


    	IAxis xAxis = chart.getAxisSet().getXAxis(0);
    	IAxis yAxis = chart.getAxisSet().getYAxis(0);

		xAxis.enableCategory(true) ;
    	
		// adjust the axis range
//		try {
			yAxis.adjustRange();
			xAxis.adjustRange();

//		} catch (SWTException e) {
//			// TODO: handle exception
//		}
		chart.setRedraw(true);
		chart.redraw();
		
    }

}
