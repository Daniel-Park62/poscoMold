package mold.posco.part;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings("serial")
public class RepeatWidget extends Composite {

	Bundle bundle = FrameworkUtil.getBundle(this.getClass());
//	ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
//	URL url2 = FileLocator.find(bundle, new Path("images/moteicon_active.png"), null);
//	ImageDescriptor icon_active = ImageDescriptor.createFromURL(url2);
//	Image img_active = resourceManager.createImage(icon_active);
//	URL urlin = FileLocator.find(bundle, new Path("images/moteicon_inactive.png"), null);
//	ImageDescriptor icon_inactive = ImageDescriptor.createFromURL(urlin);
//	Image img_inactive = resourceManager.createImage(icon_inactive);
//	URL urllow = FileLocator.find(bundle, new Path("images/moteicon_lowbattery.png"), null);
//	ImageDescriptor icon_low = ImageDescriptor.createFromURL(urllow);
//	Image img_low = resourceManager.createImage(icon_low);
	Image img_active = SWTResourceManager.getImage("images/mote_active.png");
	Image img_inactive = SWTResourceManager.getImage("images/mote_inactive.png");
	Image img_low = SWTResourceManager.getImage("images/mote_low.png");
	
	Image img_active2 = SWTResourceManager.getImage("images/mote_active2.png");
	Image img_inactive2 = SWTResourceManager.getImage("images/mote_inactive2.png");
	Image img_low2 = SWTResourceManager.getImage("images/mote_low2.png");

	String sloc ;
	String id ;
	int idx ;
	int imgk ;
	Label lbl = new Label(this, SWT.CENTER);
	
	public RepeatWidget(Composite parent, String id, int x, int y) {
		super(parent, SWT.NONE);
		this.setSize(60, 60);
		this.imgk = x ;
//		img_inactive2 = resize( img_inactive2, 40 , 40  ) ;
		this.setBackgroundImage(x == 0 ? img_active : img_active2 );
		this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));
		this.id  = id ;
		this.setLocate(x, y);
		lbl.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 14, SWT.BOLD));
		if (x == 0)
			lbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		else
			lbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
		lbl.setText(id);
		lbl.setBounds(0,20,60, 45);
		lbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		lbl.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

	}
	
	public void setImage(String id, int x) {
		setId(id);
		setImage(x);
	}
	public void setLocate(int x, int y ) {
		this.setLocation(x , y );
	}
	public void setImage(int x) {
		if (x == 2) {
			this.setBackgroundImage(imgk == 0 ? img_active : img_active2);
		} else if (x == 1) {
			this.setBackgroundImage(imgk == 0 ? img_inactive : img_inactive2);
		} else {
			this.setBackgroundImage(imgk == 0 ? img_low : img_low2);
		}
	}

	public void setId(String id) {
		this.id = id;
		lbl.setText(id);
		lbl.requestLayout();
	}


	public int getIdx() {
		return idx;
	}
	
	private Image resize(Image image, int width, int height) {
		  Image scaled = new Image(Display.getCurrent(), width, height);
		  GC gc = new GC(scaled);
		  gc.setAntialias(SWT.ON);
		  gc.setInterpolation(SWT.HIGH);
		  gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		  gc.dispose();
//		  image.dispose(); // don't forget about me!
		  return scaled;
		}


}
