package mold.posco.part;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import mold.posco.model.MoteStatus;

import org.eclipse.swt.widgets.Group;

public class NewMoteDlg extends Dialog {

	private MoteStatus mote = new MoteStatus();
	private boolean modflag = true;
	private Text txtSeq , txtStand;
	private Text txtDesc ;
	private Text txtMold ;
	private DateText txtBattdt ;
	private Button buttonS , buttonR , btnNo, btnYes;
	private Combo cbloc , cbplace;
	private DateFormat dateFmt1 = new SimpleDateFormat("yyyy-MM-dd");	
	final Font font = SWTResourceManager.getFont("Calibri", 14, SWT.NORMAL);
	final Font fonth = SWTResourceManager.getFont("맑은 고딕", 13, SWT.NORMAL);

	protected NewMoteDlg(Shell parentShell) {
		super(parentShell);
		
		// TODO Auto-generated constructor stub
	}
	protected NewMoteDlg(Shell parentShell, boolean flag) {
		super(parentShell);
		this.modflag = flag;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setFont(SWTResourceManager.getFont("나눔고딕코딩", 12, SWT.NORMAL));
		// TODO Auto-generated method stub
		Composite container = (Composite)super.createDialogArea(parent);

        GridLayout layout = new GridLayout(2, false);
        layout.marginRight = 5;
        layout.marginLeft = 10;
        
        container.setLayout(layout);

        Label lblSeq = new Label(container, SWT.NONE  );
        lblSeq.setFont(font);
        lblSeq.setText("Seq:");
        lblSeq.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

        txtSeq = new Text(container, SWT.BORDER);
        txtSeq.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                1, 1));
        txtSeq.setFont(font);

        txtSeq.addModifyListener(e -> {
            Text textWidget = (Text) e.getSource();
            mote.setSeq(Integer.parseInt(textWidget.getText()));
        });
        txtSeq.setEnabled(modflag);

        Label lblDesc = new Label(container, SWT.NONE);
        lblDesc.setText("Description:");
        lblDesc.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblDesc.setFont(font);

        txtDesc = new Text(container, SWT.BORDER);
        txtDesc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        txtDesc.setFont(font);
        txtDesc.addModifyListener(e -> {
        	Text textWidget = (Text) e.getSource();
        	mote.setDesc(textWidget.getText());
        });

        Label label = new Label(container, SWT.NONE);
        label.setText("Mote 구분:");
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label.setFont(fonth);
        // Group
        Group  genderGroup = new Group (container, SWT.NONE);
        genderGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
        genderGroup.setFont(new Font(null, "", 1, SWT.NORMAL));
 
        // Radio - mss
        buttonS = new Button(genderGroup, SWT.RADIO);
        buttonS.setText("Sensor");        
        buttonS.setFont(font);
        // Radio - mrs
        buttonR = new Button(genderGroup, SWT.RADIO);
        buttonR.setText("Repeater");
        buttonR.setFont(font);
        
        Label label_1 = new Label(container, SWT.NONE);
        label_1.setFont(fonth);
        label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label_1.setText("예비품여부:");
        
        Group group = new Group(container, SWT.NONE);
        group.setLayout(new RowLayout(SWT.HORIZONTAL));
        group.setFont(SWTResourceManager.getFont( "", 1, SWT.NORMAL));
        
        btnNo = new Button(group, SWT.RADIO);
        btnNo.setFont(font);
        btnNo.setText("No");
        
        btnYes = new Button(group, SWT.RADIO);
        btnYes.setFont(font);
        btnYes.setText("Yes");
        
        Label label_2 = new Label(container, SWT.NONE);
        label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label_2.setText("배터리교체일:");
        label_2.setFont(fonth);
        
        txtBattdt = new DateText(container, SWT.BORDER);
        txtBattdt.setFont(font);
        txtBattdt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label label_4 = new Label(container, SWT.NONE);
        label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label_4.setText("장소:");
        label_4.setFont(fonth);
        
        cbplace = new Combo(container, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
        cbplace.setFont(font);
        cbplace.setItems( AppMain.PLACE_NM   );
        cbplace.select(0);
        cbplace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label label_5 = new Label(container, SWT.NONE);
        label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label_5.setText("Mold#:");
        label_5.setFont(fonth);
        
        txtMold = new Text(container, SWT.BORDER);
        txtMold.setFont(font);
        txtMold.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label label_3 = new Label(container, SWT.NONE);
        label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label_3.setText("설치위치:");
        label_3.setFont(fonth);
        
        cbloc = new Combo(container, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
        cbloc.setFont(font);
        cbloc.setItems( AppMain.locname   );
        cbloc.select(0);
        cbloc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        label_3 = new Label(container, SWT.NONE);
        label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        label_3.setText("Stand No:");
        label_3.setFont(fonth);
        
        txtStand = new Text(container, SWT.BORDER );
        txtStand.setFont(font);
        txtStand.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        txtStand.addListener(SWT.Verify , new Listener() {
	    	@Override
	    	public void handleEvent(Event e) {
	    		// validation - mine was for an Integer (also allow 'enter'):

	    		e.doit = "0123456789".indexOf(e.text) >= 0 || e.character == '\0';
	    	}
        });

        setValue();

		return container ;
	}
	
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "저장", true).setFont(fonth);
        createButton(parent, IDialogConstants.CANCEL_ID, "취소", false).setFont(fonth);

//        parent.getShell().pack();
    }

    @Override
    protected Point getInitialSize() {
        return new Point(500, 500);
    }

    @Override
    protected void okPressed() {
    	if (modflag) mote.setSeq(Integer.parseInt(txtSeq.getText()));
    	mote.setDesc(txtDesc.getText());
    	mote.setGubun(buttonS.getSelection() ? "S" : "R");
    	mote.setSpare(btnYes.getSelection() ? "Y" : "N");
    	if ( txtBattdt.getText().replaceAll("-", "").matches("\\d{8}") )
    		mote.setBattDt(txtBattdt.getText().replaceAll("-", ""));
    	mote.setLoc( (short)cbloc.getSelectionIndex() );
    	mote.setPlace((short)cbplace.getSelectionIndex() );
    	mote.setMold(txtMold.getText());
    	mote.setStand(Short.valueOf( "".equals( txtStand.getText() ) ? "0" : txtStand.getText() ) );

        super.okPressed();
    }
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Mote 등록");
    }
    
	public MoteStatus getMote() {
		return mote;
	}

	public void setMote(MoteStatus mote) {
		this.mote = mote;
	}
	
	private void setValue() {
        txtSeq.setText(String.valueOf(mote.getSeq()));

        txtDesc.setText(mote.getDesc());
        buttonS.setSelection(mote.getGubun().equals("S"));
        buttonR.setSelection(mote.getGubun().equals("R"));
        btnNo.setSelection(mote.getSpare().equals("N"));
        btnYes.setSelection(mote.getSpare().equals("Y"));

        if (mote.getBattDt().length() == 8) txtBattdt.setText( mote.getBattDt() );
	    cbloc.select( mote.getLoc() );
	    cbplace.select( mote.getPlace() );
	    txtMold.setText("" + mote.getMold());
	    txtStand.setText(String.valueOf(mote.getStand()));
        
	}
}
