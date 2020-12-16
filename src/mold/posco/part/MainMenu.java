package mold.posco.part;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainMenu  {
		
		public MainMenu(Composite parent) {
			Image categoryicon_1 = SWTResourceManager.getImage( "images/categoryicon_1.png");
			categoryicon_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			Image categoryicon_2 = SWTResourceManager.getImage( "images/categoryicon_2.png");
			Image categoryicon_3 = SWTResourceManager.getImage( "images/categoryicon_3.png");
			Cursor handc = AppMain.handc;
			Label lblDashboard;

			Font fontMenu = SWTResourceManager.getFont( "Calibri", 16, SWT.NORMAL);

			Composite composite_4 = new Composite(parent, SWT.NONE);
			GridLayout gl_4 = new GridLayout(1, false);
			gl_4.marginLeft = 40;
			gl_4.marginTop = 15 ;
			gl_4.verticalSpacing = 15;
			gl_4.marginBottom = 15 ;
			
			composite_4.setLayout(gl_4);
//			gd_composite_4.heightHint = 50;
			composite_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			composite_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
//			composite_4.setBackground(new Color(Display.getCurrent(), 159, 170, 222));
			composite_4.setBackgroundMode(SWT.INHERIT_FORCE);

			Label lblNewLabel = new Label(composite_4, SWT.NONE);
//			lblNewLabel.setLocation(40, 3);
			lblNewLabel.setSize(47, 33);
			lblNewLabel.setBackground(parent.getBackground());
			lblNewLabel.setImage(categoryicon_1);

//			Composite composite_6 = new Composite(parent, SWT.NONE);
//			GridLayout gl_composite_6 = new GridLayout(1, false);
//			gl_composite_6.marginLeft = 40;
//			gl_composite_6.verticalSpacing = 10;
//			composite_6.setLayout(gl_composite_6);
//			GridData gd_composite_6 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
//			gd_composite_6.heightHint = 60;
//			composite_6.setLayoutData(gd_composite_6);
//			composite_6.setBackground(new Color(Display.getCurrent(), 159, 170, 222));
//			composite_6.setBackground(parent.getBackground());

			lblDashboard = new Label(composite_4, SWT.BOLD);
			lblDashboard.setFont(SWTResourceManager.getFont( "Calibri", 22, SWT.BOLD));
			lblDashboard.setCursor(handc);
			lblDashboard.setText("Dashboard");
			lblDashboard.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblDashboard.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					if (AppMain.cur_comp.getToolTipText() == "DashBoard" ) return;
					AppMain.delWidget(AppMain.cur_comp);
					new DashBoard(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.layout();
					AppMain.cur_comp.setToolTipText("DashBoard");
				}
			});

			Label lblDash2 = new Label(composite_4, SWT.BOLD);
			lblDash2.setFont(fontMenu);
			lblDash2.setCursor(handc);
			lblDash2.setText("Mote/Sensor Status");
			lblDash2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblDash2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					AppMain.delWidget(AppMain.cur_comp);
					new DashBoard2(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.layout();
					AppMain.cur_comp.setToolTipText("Status");
				}
			});

			// monitoring
			GridLayout gl_8 = new GridLayout(1, false);
			gl_8.marginLeft = 40;
			gl_8.marginTop = 15 ;
			gl_8.verticalSpacing = 15;
			gl_8.marginBottom = 15 ;
			Composite composite_8 = new Composite(parent, SWT.NONE);
			GridData gd_composite_8 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			composite_8.setLayout(gl_8);
			composite_8.setLayoutData(gd_composite_8);
			composite_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

			Label lblNewLabel4 = new Label(composite_8, SWT.NONE);
			lblNewLabel4.setSize(47, 33);
			lblNewLabel4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lblNewLabel4.setImage(categoryicon_2);

			Label lblNewreal = new Label(composite_8, SWT.NONE);

			lblNewreal.setFont(fontMenu);
			lblNewreal.setCursor(SWTResourceManager.getCursor( SWT.CURSOR_HAND));
			lblNewreal.setText("Sensor Monitoring");
			lblNewreal.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblNewreal.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					AppMain.delWidget(AppMain.cur_comp);
					AppMain.cur_comp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					new RealTime(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.layout();
					AppMain.cur_comp.setToolTipText("RealTime");

				}
			});

			
			Label lblNewLabel5 = new Label(composite_8, SWT.NONE);
			lblNewLabel5.setFont(fontMenu);
			lblNewLabel5.setCursor(SWTResourceManager.getCursor(  SWT.CURSOR_HAND));
			lblNewLabel5.setText("Active Sensor Monitoring");
			lblNewLabel5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblNewLabel5.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					AppMain.delWidget(AppMain.cur_comp);
					new SensorMap(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.layout();
					AppMain.cur_comp.setToolTipText("SensorMap");
				}
			});

			Label lblChart = new Label(composite_8, SWT.NONE);
			lblChart.setCursor(SWTResourceManager.getCursor(  SWT.CURSOR_HAND));
			lblChart.setFont(fontMenu);
			lblChart.setText("Active Chart");
			lblChart.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			
			lblChart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					AppMain.delWidget(AppMain.cur_comp);
					AppMain.cur_comp.setLayout(new FillLayout());
					new RealChart(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					AppMain.cur_comp.setSize(AppMain.cur_comp.getParent().getSize());
					AppMain.cur_comp.getParent().layout();
					AppMain.cur_comp.setToolTipText("Activechart");

				}
			});

			Label lblChart2 = new Label(composite_8, SWT.NONE);
			lblChart2.setCursor(SWTResourceManager.getCursor(  SWT.CURSOR_HAND));
			lblChart2.setFont(fontMenu);
			lblChart2.setText("Chart View");
			lblChart2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			
			lblChart2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					AppMain.delWidget(AppMain.cur_comp);
					AppMain.cur_comp.setLayout(new FillLayout());
					new ViewChart(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					AppMain.cur_comp.setSize(AppMain.cur_comp.getParent().getSize());
					AppMain.cur_comp.getParent().layout();
					AppMain.cur_comp.setToolTipText("ViewChart");
				}
			});

			Composite composite_12 = new Composite(parent, SWT.NONE);
			composite_12.setLayout(new GridLayout(1, false));
			GridData gd_composite_12 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_composite_12.heightHint = 50;
			composite_12.setLayoutData(gd_composite_12);
			composite_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));


			Label label = new Label(composite_12, SWT.SEPARATOR | SWT.HORIZONTAL);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			// configuration
			Composite composite_13 = new Composite(parent, SWT.NONE);
			composite_13.setLayout(gl_4);
			composite_13.setLayoutData(gd_composite_8);
			composite_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			// composite_8.setBackground(new Color (Display.getCurrent(), 159, 170, 222));

			Label lblNewLabel8 = new Label(composite_13, SWT.NONE);
			lblNewLabel8.setLocation(40, 2);
			lblNewLabel8.setSize(42, 42);
			lblNewLabel8.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lblNewLabel8.setImage(categoryicon_3);

/*
			Label lblNewLabel10 = new Label(composite_13, SWT.NONE);
			lblNewLabel10.setFont(fontMenu);
			lblNewLabel10.setText("Setup MasterMote");
			lblNewLabel10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblNewLabel10.setCursor(SWTResourceManager.getCursor(  SWT.CURSOR_HAND));
			lblNewLabel10.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					PocMain.delWidget(PocMain.cur_comp);
					new SensorPos(PocMain.cur_comp, SWT.NONE);
					PocMain.cur_comp.layout();
				}
			});
*/
			Label lblNewLabel11 = new Label(composite_13, SWT.NONE);
			lblNewLabel11.setCursor(SWTResourceManager.getCursor(  SWT.CURSOR_HAND));
			lblNewLabel11.setFont(fontMenu);
			lblNewLabel11.setText("Register Mote  ");
			lblNewLabel11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblNewLabel11.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDown(MouseEvent e) {
					AppMain.delWidget(AppMain.cur_comp);
					new RegMote(AppMain.cur_comp, SWT.NONE);
					AppMain.cur_comp.layout();
					AppMain.cur_comp.setToolTipText("RegMote");
				}
			});
			
			Label ldawin = new Label(parent, SWT.NONE);
			ldawin.setFont(SWTResourceManager.getFont( "Calibri", 12, SWT.ITALIC));
			ldawin.setText("CopyRight 2019 by DawinICT");
			ldawin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			ldawin.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			ldawin.setLayoutData(new GridData(SWT.CENTER,SWT.BOTTOM,true,true));


		}	
}
