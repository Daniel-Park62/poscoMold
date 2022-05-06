package mold.posco.part;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisTick;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.wb.swt.SWTResourceManager;

import mold.posco.model.ChartData;
import mold.posco.model.FindMoteInfo;

public class RealChart {
	private Chart chart ;
	FindMoteInfo findMoteinfo = new FindMoteInfo();
	
	private static final double[] yS1 = { 30,31,32,33,34,35,36,37,38,37,35,34,33,32,31,30,29,27};
	Cursor handc = AppMain.handc ;
	Cursor busyc = AppMain.busyc ;
	Cursor curc ;
	String[] slist,  slistA;
	private ILineSeries[] lineSeries  ; 
	private Color[] colors = {
			SWTResourceManager.getColor(SWT.COLOR_BLUE),
			SWTResourceManager.getColor(SWT.COLOR_RED),
			SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN),
			SWTResourceManager.getColor(SWT.COLOR_GREEN),
			SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW),
			SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA),
			SWTResourceManager.getColor(SWT.COLOR_YELLOW),
			SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY),
			SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN),
			SWTResourceManager.getColor(SWT.COLOR_CYAN),
			SWTResourceManager.getColor(SWT.COLOR_MAGENTA),
			
	};
	private int axisId ;
	final Color BLACK = SWTResourceManager.getColor(SWT.COLOR_BLACK);
	final Color RED = SWTResourceManager.getColor(SWT.COLOR_RED);
	final Color BLUE = SWTResourceManager.getColor(SWT.COLOR_BLUE);
	final Color GREEN = SWTResourceManager.getColor(SWT.COLOR_GREEN);
	final Color CYAN = SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN);
	/**
     * Create the composite.
     *
     * @param parent
     * @param style
     * @wbp.parser.entryPoint
     */

//	int sel = 0;
	Combo cbddown;
	String seltext = "";
	short gtemp = 0 ;
	Label lblDate, lblTime,  lblfrom ,lblfromd, lblto, lbltod ;
	DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private DateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Timestamp time_c = Timestamp.valueOf("1900-01-01 00:00:00") ;
	
    Thread uiUpdateThread ;
	int px ;
	double py ;
	Spinner spinner ;
	
    public RealChart(Composite parent, int style) {
    	this( parent,  style, 0 , "ALL") ;
    }
    public RealChart(Composite parent, int style, int thgb, String sno) {

    	this.gtemp = (short)thgb ;
    	seltext = sno ;
    	
	    final Font font2 = SWTResourceManager.getFont("Consolas", 14, SWT.NORMAL);
	    Font font21 = SWTResourceManager.getFont("Consolas", 12, SWT.NORMAL);
	    Font font21b = SWTResourceManager.getFont("Consolas", 12, SWT.BOLD);
	    final Image slice_page = SWTResourceManager.getImage("images/slice_page6.png");
	    
	    Color COLOR_T = SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT) ;
    	
    	SashForm sash1 = new SashForm(parent, SWT.VERTICAL);
    	Composite comps1 = new Composite(sash1, SWT.NONE);
    	sash1.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				if (uiUpdateThread != null ) uiUpdateThread.stop();
			}
		});

    	comps1.setBackground(parent.getBackground());
		GridLayout gl_in = new GridLayout(2,true);
		gl_in.marginRight = 50;
		gl_in.marginLeft = 65;
		
//		comps1.setLayout(gl_in);
		GridData gd_in = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_in.heightHint = 30;
		gd_in.minimumHeight = 30;

		comps1.setLayoutData(gd_in);
		comps1.setBackgroundImage(slice_page);
		
		lblDate = new Label(comps1, SWT.NONE);
		
		lblDate.setBounds(340, 60, 160, 30);
		lblDate.setFont(new Font(null, "Microsoft Sans Serif", 16, SWT.NORMAL));
		lblDate.setText("");
		lblDate.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		lblDate.setBackground(new Color (Display.getCurrent(), 141,153,208));

		lblTime = new Label(comps1, SWT.NONE);
		lblTime.setBounds(340, 90 , 160, 30);
		lblTime.setFont(new Font(null, "Microsoft Sans Serif", 22, SWT.NORMAL));
		lblTime.setText("");
		lblTime.setBackground(new Color (Display.getCurrent(), 141,153,208));
		lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Composite comp_in = new Composite(comps1, SWT.NONE) ;
//		comp_in.setLayout(new GridLayout(12, false));
		comp_in.setBounds(600, 120, 800, 50);
//		comp_in.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		comp_in.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		comp_in.moveAbove(lblTime);
        Group  pGroup = new Group (comp_in, SWT.NONE);
        pGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
        pGroup.setFont(SWTResourceManager.getFont( "", 1, SWT.NORMAL));
        pGroup.setBackground(COLOR_T);
        
//        pGroup.setBounds(0,0, 200, 40);
		
        {
        	Button bp0 = new Button(pGroup, SWT.RADIO);
        	bp0.setText("온도");
        	bp0.setFont(font21b);
        	bp0.setSelection(gtemp == 0);
        	Button bp1 = new Button(pGroup, SWT.RADIO);
        	bp1.setText("습도");
        	bp1.setFont(font21b);
        	bp1.setSelection(gtemp == 1);
        	bp0.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        	bp1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        	bp0.pack();
        	bp1.pack();
//        	pGroup.pack();
        	pGroup.setBounds(0,5,140,30);
        	
        	bp0.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					gtemp = 0 ;
				}
			});
        	bp1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					gtemp = 1 ;
				}
			});

        }
        	Label lbl = new Label(comp_in,SWT.NONE) ;
			lbl.setText("*ID Select");
			lbl.setBackground(COLOR_T);
			lbl.setFont(font2);
//			lbl.pack();
			lbl.setBounds(155,7,110, 30);
//			lbl.setLayoutData(new GridData(200,30));
       

		slist = findMoteinfo.getSensorList() ;
		slistA = new String[slist.length + 1] ;
		
		System.arraycopy(new String[] {"ALL"}, 0, slistA, 0, 1);
		System.arraycopy(slist,0,slistA, 1, slist.length ) ;
	    lineSeries = new ILineSeries[slist.length] ; 

		cbddown = new Combo(comp_in, SWT.DROP_DOWN | SWT.BORDER | SWT.CENTER);
		cbddown.setFont(font2);
		cbddown.setItems(slistA);
		cbddown.setText(seltext);
//		cbddown.pack();
		cbddown.setBounds(265, 5, 80,40 );

		cbddown.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
//				sel = cbddown.getSelectionIndex();
				seltext = cbddown.getText() ;
				try {
					setYdata();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		Label lblrg = new Label(comp_in, SWT.NONE);
		lblrg.setText(" *범위(분)");
		lblrg.setFont(font2);
		lblrg.setBounds(360, 10, 100, 30);
		lblrg.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
	    spinner = new Spinner(comp_in, SWT.BORDER | SWT.CENTER | GridData.CENTER);
	    spinner.setMinimum(10);
	    spinner.setMaximum(180);
	    spinner.setSelection(60);
	    spinner.setIncrement(5);
	    spinner.setFont(font2);
	    spinner.setBounds(465, 5, 60, 35);
		
		final Button bstart = new Button(comp_in, SWT.ARROW | SWT.RIGHT);
		final Button bpause = new Button(comp_in, SWT.PUSH | SWT.CENTER);
		bpause.setCursor(handc);
		bpause.setFont(font2);
		bpause.setText(" PAUSE ");
		bpause.setBounds(540, 5, 80, 35);
		bpause.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				uiUpdateThread.interrupt();
				uiUpdateThread = null;
				bstart.setEnabled(true);
				bpause.setEnabled(false);
			}
		}); 
		bpause.setLayoutData(new GridData(SWT.FILL, GridData.CENTER, true, false));
		
		bstart.setCursor(handc);
		bstart.setFont(font2);
		bstart.setToolTipText("Play");
		bstart.setBounds(630, 5, 80, 35);
		bstart.setEnabled(false);
		bstart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( uiUpdateThread == null) {
					uiUpdateThread = new MyThread(Display.getCurrent(), AppMain.MOTECNF.getMeasure() * 1000 ) ;
					uiUpdateThread.start();
					bstart.setEnabled(false);
					bpause.setEnabled(true);
				}
			}
		}); 
		bstart.setLayoutData(new GridData(SWT.FILL, GridData.CENTER, true, false, 1, 1));

		
		chart = new Chart(sash1, SWT.NONE);
		chart.setBackground(parent.getBackground());
		chart.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_CROSS));
		Composite compb = new Composite(sash1, SWT.NONE);
		compb.setSize(600, -1);
		compb.setBackground(parent.getBackground());
		compb.setLayout(new GridLayout(5, false)) ;
		lblfrom = new Label(compb,SWT.FILL );
		lblfromd = new Label(compb,SWT.BORDER);
		lblto = new Label(compb,SWT.NONE);
		lbltod = new Label(compb,SWT.BORDER);
		lblfrom.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER, false, true, 1, 1));
		lblfromd.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER, false, true, 1, 1));
		lblto.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER, false, true, 1, 1));
		lbltod.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER, false, true, 1, 1));
		lblfrom.setFont(font2) ;
		lblto.setFont(font2);
		lblfromd.setFont(new Font(null, "MS Gothic", 14, SWT.BOLD )) ;
		lbltod.setFont(new Font(null, "MS Gothic", 14, SWT.BOLD )) ;
		lblfrom.setText(" 조회기간  Date/Time ");
		lblto.setText(" ~ ");
    	lblfromd.setText(" " + datefmt.format(time_c)) ; 
    	lbltod.setText(" " + datefmt.format(time_c)) ;
		compb.pack();
		lblfrom.setBackground(parent.getBackground());
		lblto.setBackground(parent.getBackground());
		lblfrom.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
		lblto.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
		
		chart.setLayoutData(new FillLayout());
		// set titles
		chart.getTitle().setText("온/습도 Chart");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Time(Sec)");
		chart.getAxisSet().getYAxis(0).getTitle().setText("");
		// create second Y axis
//		axisId = chart.getAxisSet().createYAxis();
		axisId = 0 ;
		
		// set the properties of second Y axis
//		IAxis yAxis2 = chart.getAxisSet().getYAxis(axisId);
//		yAxis2.setPosition(Position.Secondary);
		
		// create line series
		for (int ix = 0; ix < slist.length ; ix++) {
			lineSeries[ix] = (ILineSeries)chart.getSeriesSet().createSeries(SeriesType.LINE, slist[ix]);
			lineSeries[ix].setYSeries(yS1);
		}

		chart.getLegend().setFont(new Font(Display.getCurrent(),"Calibri", 14, SWT.BOLD));
		// adjust the axis range
//		chart.getAxisSet().adjustRange();
		sash1.setWeights(new int[] {23,90,5});
		
		final IAxis xAxis = chart.getAxisSet().getXAxis(0);
		final IAxis yAxis = chart.getAxisSet().getYAxis(0);
		xAxis.getTick().setForeground(BLACK);
		xAxis.getTick().setFont(new Font(Display.getCurrent(),"굴림", 10, SWT.BOLD));
		xAxis.getTitle().setForeground(BLACK);

		yAxis.getTick().setForeground(BLACK);
		yAxis.getTitle().setForeground(BLACK);
		yAxis.getTitle().setText("온/습도");
		yAxis.getTick().setFont(new Font(Display.getCurrent(),"Calibri", 12, SWT.BOLD));
//		yAxis.getTick().setFormat(new DecimalFormat("#,###"));
	    IAxisTick xTick = xAxis.getTick();
	    xTick.setFormat(timeFormat);
		
		Composite plot = (Composite)chart.getPlotArea() ;
		plot.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				chart.setRedraw(false);
				if (arg0.button == 1) { 
					chart.getAxisSet().getXAxis(0).zoomIn(px);
					chart.getAxisSet().getYAxis(0).zoomIn(py);
				};
				if (arg0.button == 3) { 
					chart.getAxisSet().getXAxis(0).zoomOut(px);
					chart.getAxisSet().getYAxis(0).zoomOut(py);
					if (chart.getAxisSet().getXAxis(0).getRange().lower < 0) {
						chart.getAxisSet().getXAxis(0).scrollUp();
					}
				};
				chart.setRedraw(true);
				chart.setFocus();
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		plot.addMouseMoveListener(new MouseMoveListener() {

			public void mouseMove(MouseEvent e) {

				Date x = new Date((long)chart.getAxisSet().getXAxis(0).getDataCoordinate(e.x));
				px = (int)xAxis.getDataCoordinate(e.x);
				py = yAxis.getDataCoordinate(e.y);
//				if (px >= 0 && px < catdate.length ) 
				if (x != null ) 
				try {
					plot.setToolTipText(timeFormat.format(x) + 
							(gtemp == 0 ? "\n 온도: " : "\n 습도: ") + String.format("%,2.2f", py));	
				} catch (Exception e2) {
					System.out.println(e2 + ": " + px + ":" + py);
				}
				
			}
		});
		
		chart.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent arg0) {
				int wheelCount = (int) Math.ceil(arg0.count / 3.0f);
				chart.setRedraw(false);
				while (wheelCount != 0) {
						
						if (wheelCount > 0) {
							chart.getAxisSet().getYAxis(0).scrollUp();
							wheelCount-- ;
						} else {
							chart.getAxisSet().getYAxis(0).scrollDown();
							wheelCount++ ;
						}
				}
				
				/*
				wheelCount = (int) Math.ceil(wheelCount / 3.0f);
				Range yrange = yAxis.getRange();

				yrange.lower +=  50 * wheelCount;
				yrange.upper -=  50 * wheelCount;

				try {
					yAxis.setRange(yrange);
				} catch (Exception e) {

				}
				*/
				chart.setRedraw(true);
//				chart.redraw();
                  
			}
		});
		setYdata();
		uiUpdateThread = new MyThread(Display.getCurrent(), AppMain.MOTECNF.getMeasure() * 1000 ) ;
		uiUpdateThread.start();

    }
    

	private class MyThread extends Thread {
		private Display display = null;
		private int interval ;
		MyThread(Display display, int interval){
			this.display = display ;
			this.interval = interval ;
		}
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted() && !chart.isDisposed() ) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							setYdata();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				});
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
//					e.printStackTrace();
					break;
				}

			}
		}

	}

//	String[] catdate = {""};
	
    private void setYdata() {
		curc = chart.getCursor();
		chart.setCursor(busyc);

    	ArrayList<ChartData> arrayinfo ;
    	
    	IAxis xAxis = chart.getAxisSet().getXAxis(0);

    	try {
    		for (int ix = 0; ix < lineSeries.length; ix++ )
    			lineSeries[ix].setVisible(false);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		double[][] ydv = new double[slist.length][] ;
		Date[][] xdv = new Date[slist.length][] ;
    	time_c = findMoteinfo.getLasTime() ;
    	
    	arrayinfo = findMoteinfo .getChartData(0, gtemp,spinner.getSelection() * 60); 

		/*
		 * catdate = arrayinfo.stream().map(a -> datefmt.format(a.getTm()) )
		 * .distinct().toArray(String[]::new) ;
		 */
    	chart.setRedraw(false);
    	for (int ix = 0; ix < slist.length; ix++) {
    		final String sno = slist[ix].toString() ;
        	if (cbddown.getSelectionIndex() == 0 || seltext.equals(sno)  ) {
        		int i2 = 0;

        		ydv[ix] =  arrayinfo.stream().filter(m -> m.getDesc().equals(sno))
        				.mapToDouble(a -> a.getTemp())
        				.toArray() ;
        		xdv[ix] = arrayinfo.stream().filter(m -> m.getDesc().equals(sno)).map(a -> a.getTm()).toArray(Date[]::new) ;
        		lineSeries[ix].setYSeries(ydv[ix]);
        		lineSeries[ix].setXDateSeries(xdv[ix]);
        		if (ix < 8) {
	        		lineSeries[ix].setSymbolColor(colors[ix]);
	        		lineSeries[ix].setLineColor(colors[ix]);
        		}

        		lineSeries[ix].setYAxisId(axisId);
        		lineSeries[ix].setSymbolType(PlotSymbolType.NONE);
        		lineSeries[ix].setSymbolSize(2);
//        		lineSeries[ix].setLineStyle(LineStyle.NONE);
        		lineSeries[ix].setAntialias(SWT.ON);
        		lineSeries[ix].setVisible(true);
        	}
    	}
    	
    	lblfromd.setText(" " + datefmt.format(arrayinfo.size() > 0 ? arrayinfo.get(0).getTm() : time_c)) ; 
    	lbltod.setText(" " + datefmt.format(time_c)) ;
		lblDate.setText(dateFormat.format(time_c));
		lblTime.setText(  timeFormat.format(time_c));
		lblDate.pack();
		lblTime.pack();
		chart.setFocus();

    	xAxis.enableCategory(true) ;
    	
		// adjust the axis range
		try {
			chart.getAxisSet().adjustRange();
			IAxis yAxis = chart.getAxisSet().getYAxis(0);
			yAxis.adjustRange();
			chart.getAxisSet().getXAxis(0).adjustRange();
//			chart.redraw();
		} catch (SWTException e) {
			// TODO: handle exception
		}
		chart.setRedraw(true);
		chart.setFocus();
		chart.setCursor(curc);
		
    }

}
