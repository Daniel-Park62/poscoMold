package mold.posco.part;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import org.eclipse.wb.swt.SWTResourceManager;

import mold.posco.model.MoteConfig;
import mold.posco.model.Vstatus;

public class AppMain extends ApplicationWindow {

    public static EntityManagerFactory emf ; //= Persistence.createEntityManagerFactory("mold.posco");
    final public static Cursor handc = SWTResourceManager.getCursor( SWT.CURSOR_HAND);
    final public static Cursor busyc = SWTResourceManager.getCursor( SWT.CURSOR_WAIT);
    public static MoteConfig MOTECNF = MoteConfig.getInstance() ;
    public static Vstatus VSTATUS = Vstatus.getInstance() ;
    
    final public static String[] locname = {"","좌상","좌하","우상","우하" } ;
	final public static Color colact = SWTResourceManager.getColor(49,136,248) ;
	final public static Color colinact = SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY) ;
	final public static Color collow = SWTResourceManager.getColor(245,174,0) ;
	final public static Color colout = SWTResourceManager.getColor(SWT.COLOR_RED) ;
	public static short PLACE = 0;
	final public static Font fontT = SWTResourceManager.getFont("Microsoft Sans Serif", 22, SWT.NORMAL ) ;
	final public static Font tblfont1 = SWTResourceManager.getFont("Microsoft Sans Serif", 13, SWT.NORMAL ) ;
	final public static Font font1 = SWTResourceManager.getFont("맑은 고딕", 13, SWT.NORMAL ) ;
	final public static Font font1b = SWTResourceManager.getFont("맑은 고딕", 13, SWT.BOLD ) ;
	final public static Font font2 = SWTResourceManager.getFont("맑은 고딕", 12, SWT.NORMAL ) ;
	final public static Font font2b = SWTResourceManager.getFont("맑은 고딕", 12, SWT.BOLD ) ;
	final public static Font font11 = SWTResourceManager.getFont("Microsoft Sans Serif", 11, SWT.NORMAL ) ;
	
	public static Composite cur_comp ;

	final public static String[] PLACE_NM = {"2공장","수리장" } ;

	/**
	 * Create the application window.
	 */
	public AppMain() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
        String s = System.getenv("PLACE")  ;
        if (s == null) s = "0" ;
        PLACE = Short.valueOf(s);

        String dbip = System.getenv("DBIP") ;  // 주소:port
        if (dbip == null) dbip = "localhost" ;
        String dbport = System.getenv("DBPORT") ;  // 주소:port
        if (dbport == null) dbport = "3306" ;
        
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.url", "jdbc:mariadb://" + dbip + ":" + dbport + "/mold?autoReconnect=true");
        properties.put("javax.persistence.jdbc.user", "moldusr");
        properties.put("javax.persistence.jdbc.password", "dawinit1"); 
        properties.put("javax.persistence.jdbc.driver","org.mariadb.jdbc.Driver") ;
//        
        emf = Persistence.createEntityManagerFactory("mold.posco", properties) ;
        
        EntityManager em = AppMain.emf.createEntityManager();

//		MOTECNF = em.createNamedQuery("MoteConfig.findone", MoteConfig.class).getSingleResult() ;
		MOTECNF = em.find( MoteConfig.class,(short)1) ;
		em.close();
//		addStatusLine();
	}

	public static void delWidget(Composite parent) {
		 
	    for (Control kid : parent.getChildren()) {
	    	try {
		        kid.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
//		Composite container = new Composite(parent, SWT.NONE);
		Shell shell = getShell() ;
		shell.setBounds(2, 2, 1820, 1050);
//		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setSashWidth(5);
		Composite comp1_1 = new Composite(sashForm, SWT.NONE) ;
		comp1_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		comp1_1.setLayout(new GridLayout(1, true));
		comp1_1.setBackgroundMode(SWT.INHERIT_FORCE);
 
		new MainMenu(comp1_1);
		comp1_1.pack();
		 
		Composite comp1_2 = new Composite(sashForm, SWT.NONE);
		comp1_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		comp1_2.setLayout(new FillLayout());
		comp1_2.setBackgroundMode(SWT.INHERIT_FORCE);
//		new RealTime(comp1_2, SWT.NONE);
//		new RegMote(comp1_2, SWT.NONE);
		new DashBoard(comp1_2, SWT.NONE);
		
//		new RealChart(comp1_2, SWT.NONE);
//		new SensorPos(comp1_2, SWT.NONE);

		sashForm.setWeights(new int[] {18,80});
		cur_comp = comp1_2 ;
		cur_comp.setToolTipText("DashBoard");
		return parent;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");

		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			AppMain window = new AppMain();
			window.setBlockOnOpen(true); 
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("POSCO Mold 폭가변제어장치 무선 온습도 모니터링 ");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
	
	public static int sendZero(String mac ) {

        String s = System.getenv("MONIP") ;
        if (s == null) s = "localhost" ;

		String url="http://"+ s + ":9977/zero?mac=" + mac ; 
		if (!mac.isEmpty())
		try {
			System.out.println(url) ;
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			char[] buff = new char[512];
			int len ;
			while( (len = br.read(buff)) != -1) {
				//	           System.out.print(new String(buff, 0, len));
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println(url + " http send error !!") ;
		}
		return 1;
	}

	public static int sendMeasur(String meas ) {
        String s = System.getenv("MONIP") ;
        if (s == null) s = "localhost" ;
        System.out.println("MONIP:"+s);

		String url="http://"+ s + ":9977?meas=" + meas ;

		try {
			System.out.println(url) ;
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			char[] buff = new char[512];

			while(  br.read(buff) != -1) {
				//	           System.out.print(new String(buff, 0, len));
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println(url + " http send error !!") ;
		}
		return 1;
	}

	public static int sendReload( ) {
        String s = System.getenv("MONIP") ;
        if (s == null) s = "localhost" ;
        System.out.println("MONIP:"+s);

		String url="http://"+ s + ":9977/reload"  ;

		try {
			System.out.println(url) ;
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			char[] buff = new char[512];

			while(  br.read(buff) != -1) {
				//	           System.out.print(new String(buff, 0, len));
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println(url + " http send error !!") ;
		}
		return 1;
	}
	public static Image resize(Image image, int width, int height) {
		  Image scaled = new Image(Display.getCurrent(), width, height);
		  GC gc = new GC(scaled);
		  gc.setAntialias(SWT.ON);
		  gc.setInterpolation(SWT.HIGH);
		  gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		  gc.dispose();
		  image.dispose(); // don't forget about me!
		  return scaled;
	}

	
    public static void exportTable(TableViewer tableViewer, int si)  {
        // TODO: add logic to ask user for the file location

    	String[] ext = { "csv" }  ;
    	   final FileDialog dlg = new FileDialog ( Display.getDefault().getActiveShell() , SWT.APPLICATION_MODAL | SWT.SAVE );
    	    dlg.setFileName("sensorData");
    	    dlg.setFilterExtensions ( ext );
    	    dlg.setOverwrite ( true );
    	    dlg.setText ( "저장파일명 선택" );

    	    String fileName = dlg.open ();
    	    if ( fileName == null )
    	    {
    	        return ;
    	    }
    	    if (!fileName.matches("\\.csv$") ) fileName += ".csv" ;
//      File  file = new File(fileName + "." + ext[ dlg.getFilterIndex() ] );
    	    
    	    
//        BufferedWriter bw = new BufferedWriter(osw);
        
        try {
    		FileOutputStream fos = new FileOutputStream(fileName );
    		OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
        	BufferedWriter writer = new BufferedWriter(new BufferedWriter(osw)) ;
            final Table table = tableViewer.getTable();
            final int[] columnOrder = table.getColumnOrder();
 
            for(int columnOrderIndex = si; columnOrderIndex < columnOrder.length; 
                    columnOrderIndex++) {
                int columnIndex = columnOrder[columnOrderIndex];
                TableColumn tableColumn = table.getColumn(columnIndex);
                if (tableColumn.getText().equals("ID")) 
                	writer.write("SID");
                else
                    writer.write(tableColumn.getText());
                if ( columnOrderIndex+1 != columnOrder.length ) writer.write(",");
            }
            writer.write("\r\n");
            
            final int itemCount = table.getItemCount();
            for(int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
                TableItem item = table.getItem(itemIndex);
                
                for(int columnOrderIndex = si; 
                        columnOrderIndex < columnOrder.length; 
                        columnOrderIndex++) {
                    int columnIndex = columnOrder[columnOrderIndex];
                    writer.write(item.getText(columnIndex));
                    if ( columnOrderIndex+1 != columnOrder.length ) writer.write(",");
                }
                writer.write("\r\n");
            }
            writer.close();
        } catch(IOException ioe) {
            // TODO: add logic to inform the user of the problem
            System.err.println("trouble exporting table data to file");
            ioe.printStackTrace();
		}
        
    }
 

}
