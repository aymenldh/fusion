import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Fusion {

	private JFrame frame;
	JFrame config=new JFrame();
	JCheckBox cbCTimeSP=new JCheckBox("HH:MM:SS");
	JCheckBox cbCTimeMSP=new JCheckBox("Millisecond");
	JCheckBox cbCTimeS=new JCheckBox("HH:MM:SS");
	JCheckBox cbCTimeMS=new JCheckBox("Millisecond");	
	JCheckBox cbCReinit=new JCheckBox();
	JCheckBox cbCReinitP=new JCheckBox();

	JFileChooser chooserTelos = new JFileChooser();
	JFileChooser chooserXsens485 = new JFileChooser();
	JFileChooser chooserXsens486 = new JFileChooser();
	JFileChooser chooserXsens487 = new JFileChooser();
	JFileChooser chooserXsens488 = new JFileChooser();
	JFileChooser chooserXsens489 = new JFileChooser();
	JFileChooser chooserXsens490 = new JFileChooser();


	JLabel LNomTelos =new JLabel ("");
	JLabel lNomXsens85=new JLabel ("") ;
	JLabel lNomXsens86=new JLabel ("") ;
	JLabel lNomXsens87 =new JLabel ("");
	JLabel lNomXsens88 =new JLabel ("");
	JLabel lNomXsens89 =new JLabel ("");
	JLabel lNomXsens90 =new JLabel ("");

	String pathTelos="",pathXsens85="",pathXsens86="",pathXsens87="",pathXsens88="",pathXsens89="",pathXsens90="";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fusion window = new Fusion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Fusion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	float CalculerFrequencedechantillonage(String aux2)
	{
		StringTokenizer st1=new StringTokenizer(aux2," ");
		String aux3=st1.nextToken();
		for (int i=0;i<3;i++)
			aux3=st1.nextToken();
		aux3=aux3.substring(0,aux3.length()-2);
		return Float.parseFloat(aux3);

	}

	void reinitialiser()
	{
		LNomTelos.setText("");
		pathTelos="";

		lNomXsens85.setText("");
		pathXsens85="";

		lNomXsens86.setText("");
		pathXsens86="";

		lNomXsens87.setText("");
		pathXsens87="";

		lNomXsens88.setText("");
		pathXsens88="";

		lNomXsens89.setText("");
		pathXsens89="";

		lNomXsens90.setText("");
		pathXsens90="";

		// A completer !! ***********

	}

	void fusionTous(String path){

		BufferedReader tF;
		try {
			tF = new BufferedReader(new FileReader(pathTelos));

			try {
				String chTest=tF.readLine();

				if (chTest.indexOf(":")==-1) {
					fusionner(path);					
				}
				else 
				{
					//second
					fusionnersecond(path);					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void fusionnersecond(String path){
		int diffEntreDeuxEchantillonTelos,telosVal1,telosVal2; // en millisecond
		float freqEchantillonageXsens,periodeEchantillonageXsens;
		
		int compt;
		String aux,aux2,aux3,line;
		

		//cbCTimeMSP.setSelected(true);
		//cbCTimeMS.setSelected(true);

		if ((pathTelos!="")&&((pathXsens85!="")||(pathXsens86!="")||(pathXsens87!="")||(pathXsens88!="")||(pathXsens89!="")||(pathXsens90!=""))){

			long date1,date2;
			String lineData;			
			BufferedReader tF,xF85,xF86,xF87,xF88,xF89,xF90;
			BufferedWriter fTX;
			File f;
			boolean endFile=false;





			try {



				if (pathXsens85!="") {
					tF = new BufferedReader(new FileReader(pathTelos));
					xF85 = new BufferedReader(new FileReader(pathXsens85));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens485.txt");
					f=new File(path+"\\tXsens485.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.
						aux=xF85.readLine();
						aux=xF85.readLine();							
						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);
						aux=xF85.readLine();aux=xF85.readLine();aux=xF85.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						System.out.println("auuuuxx : "+aux);
						StringTokenizer stz=new StringTokenizer(aux, ":");
						aux2=stz.nextToken();
						System.out.println("auuuuxx2 : "+aux2);
						aux2=stz.nextToken();
						System.out.println("auuuuxx3 : "+aux2);
						aux2=stz.nextToken();
						aux2=aux2.substring(0,2);
						System.out.println("auuuuxx 4: "+aux2);
						telosVal1=Integer.parseInt(aux2);							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, ":");
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=aux2.substring(0,2);

								telosVal2=Integer.parseInt(aux2);

								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;
								if (diffEntreDeuxEchantillonTelos<0) diffEntreDeuxEchantillonTelos+=60;//si les secondes de la 2eme date inf à celles de la 1er.
								diffEntreDeuxEchantillonTelos*=1000; //pour le convertir en millisecond

								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF85.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens86!=""){
					tF = new BufferedReader(new FileReader(pathTelos));
					xF86 = new BufferedReader(new FileReader(pathXsens86));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens486.txt");
					f=new File(path+"\\tXsens486.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF86.readLine();
						aux=xF86.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF86.readLine();aux=xF86.readLine();aux=xF86.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, ":");
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=aux2.substring(0,2);
						telosVal1=Integer.parseInt(aux2);							
						System.out.println("telosVal1 = "+telosVal1);
						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, ":");
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=aux2.substring(0,2);
								telosVal2=Integer.parseInt(aux2);
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;
								if (diffEntreDeuxEchantillonTelos<0) diffEntreDeuxEchantillonTelos+=60;//si les secondes de la 2eme date inf à celles de la 1er.
								diffEntreDeuxEchantillonTelos*=1000; //pour le convertir en millisecond


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF86.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				} 



				if (pathXsens87!="") 
				{

					tF = new BufferedReader(new FileReader(pathTelos));
					xF87 = new BufferedReader(new FileReader(pathXsens87));


					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens487.txt");
					f=new File(path+"\\tXsens487.txt");
					f.createNewFile();

					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF87.readLine();
						aux=xF87.readLine();
						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF87.readLine();aux=xF87.readLine();aux=xF87.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();						
						StringTokenizer stz=new StringTokenizer(aux, ":");
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=aux2.substring(0,2);
						telosVal1=Integer.parseInt(aux2);							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{

								stz=new StringTokenizer(aux, ":");			
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=aux2.substring(0,2);
								telosVal2=Integer.parseInt(stz.nextToken());

								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;
								if (diffEntreDeuxEchantillonTelos<0) diffEntreDeuxEchantillonTelos+=60;//si les secondes de la 2eme date inf à celles de la 1er.
								diffEntreDeuxEchantillonTelos*=1000; //pour le convertir en millisecond


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF87.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens88!=""){ 

					tF = new BufferedReader(new FileReader(pathTelos));
					xF88 = new BufferedReader(new FileReader(pathXsens88));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens488.txt");
					f=new File(path+"\\tXsens488.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF88.readLine();
						aux=xF88.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF88.readLine();aux=xF88.readLine();aux=xF88.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, ":");								
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=aux2.substring(0,2);
						telosVal1=Integer.parseInt(aux2);							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{

								stz=new StringTokenizer(aux, ":");			
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=stz.nextToken();			
								aux2=aux2.substring(0,2);
								telosVal2=Integer.parseInt(aux2);
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;
								if (diffEntreDeuxEchantillonTelos<0) diffEntreDeuxEchantillonTelos+=60;//si les secondes de la 2eme date inf à celles de la 1er.
								diffEntreDeuxEchantillonTelos*=1000; //pour le convertir en millisecond


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF88.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens89!="") {

					tF = new BufferedReader(new FileReader(pathTelos));
					xF89 = new BufferedReader(new FileReader(pathXsens89));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens489.txt");
					f=new File(path+"\\tXsens489.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF89.readLine();
						aux=xF89.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF89.readLine();aux=xF89.readLine();aux=xF89.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, ":");			
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=aux2.substring(0,2);
						telosVal1=Integer.parseInt(aux2);							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{

								stz=new StringTokenizer(aux, ":");			
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=stz.nextToken();			
								aux2=aux2.substring(0,2);
								telosVal2=Integer.parseInt(aux2);
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;
								if (diffEntreDeuxEchantillonTelos<0) diffEntreDeuxEchantillonTelos+=60;//si les secondes de la 2eme date inf à celles de la 1er.
								diffEntreDeuxEchantillonTelos*=1000; //pour le convertir en millisecond


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF89.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens90!=""){

					tF = new BufferedReader(new FileReader(pathTelos));
					xF90 = new BufferedReader(new FileReader(pathXsens90));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens490.txt");
					f=new File(path+"\\tXsens490.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF90.readLine();
						aux=xF90.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF90.readLine();aux=xF90.readLine();aux=xF90.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, ":");			
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=stz.nextToken();
						aux2=aux2.substring(0,2);
						telosVal1=Integer.parseInt(aux2);							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{

								stz=new StringTokenizer(aux, ":");			
								aux2=stz.nextToken();
								aux2=stz.nextToken();
								aux2=stz.nextToken();		
								aux2=aux2.substring(0,2);
								telosVal2=Integer.parseInt(aux2);
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;

								if (diffEntreDeuxEchantillonTelos<0) diffEntreDeuxEchantillonTelos+=60;//si les secondes de la 2eme date inf à celles de la 1er.
								diffEntreDeuxEchantillonTelos*=1000; //pour le convertir en millisecond

								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF90.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				///////////////////////////////////////////////////////////////////////:************* i am here !!!

				/*while(line != null)
		             {
		                    System.out.println(line);
		                    line = in.readLine();
		             }
				 */
				//	System.out.println(line);

			} catch (IOException e) {e.printStackTrace();}



		}
	}
	void fusionner(String path){
		long diffEntreDeuxEchantillonTelos,telosVal1,telosVal2; // en millisecond
		float freqEchantillonageXsens,periodeEchantillonageXsens;
		boolean calculFreq=false;
		int compt;
		String aux,aux2,aux3,line;
		System.out.println("getSelectedFile() : " 
				+  path);

		System.out.println(pathTelos+"  test fn fusionner");


		//cbCTimeMSP.setSelected(true);
		//cbCTimeMS.setSelected(true);

		if ((pathTelos!="")&&((pathXsens85!="")||(pathXsens86!="")||(pathXsens87!="")||(pathXsens88!="")||(pathXsens89!="")||(pathXsens90!=""))){

			long date1,date2;
			String lineData;			
			BufferedReader tF,xF85,xF86,xF87,xF88,xF89,xF90;
			BufferedWriter fTX;
			File f;
			boolean endFile=false;





			try {



				if (pathXsens85!="") {
					tF = new BufferedReader(new FileReader(pathTelos));
					xF85 = new BufferedReader(new FileReader(pathXsens85));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens485.txt");
					f=new File(path+"\\tXsens485.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.
						aux=xF85.readLine();
						aux=xF85.readLine();							
						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						calculFreq=true;
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);
						aux=xF85.readLine();aux=xF85.readLine();aux=xF85.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, "com");

						telosVal1=Long.parseLong(stz.nextToken());							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, "com");								
								telosVal2=Long.parseLong(stz.nextToken());
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF85.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens86!=""){
					tF = new BufferedReader(new FileReader(pathTelos));
					xF86 = new BufferedReader(new FileReader(pathXsens86));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens486.txt");
					f=new File(path+"\\tXsens486.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF86.readLine();
						aux=xF86.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						calculFreq=true;
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF86.readLine();aux=xF86.readLine();aux=xF86.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, "com");

						telosVal1=Long.parseLong(stz.nextToken());							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, "com");								
								telosVal2=Long.parseLong(stz.nextToken());
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF86.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				} 



				if (pathXsens87!="") 
				{

					tF = new BufferedReader(new FileReader(pathTelos));
					xF87 = new BufferedReader(new FileReader(pathXsens87));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens487.txt");
					f=new File(path+"\\tXsens487.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF87.readLine();
						aux=xF87.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						calculFreq=true;
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF87.readLine();aux=xF87.readLine();aux=xF87.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, "com");

						telosVal1=Long.parseLong(stz.nextToken());							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, "com");								
								telosVal2=Long.parseLong(stz.nextToken());
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF87.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens88!=""){ 

					tF = new BufferedReader(new FileReader(pathTelos));
					xF88 = new BufferedReader(new FileReader(pathXsens88));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens488.txt");
					f=new File(path+"\\tXsens488.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF88.readLine();
						aux=xF88.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						calculFreq=true;
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF88.readLine();aux=xF88.readLine();aux=xF88.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, "com");

						telosVal1=Long.parseLong(stz.nextToken());							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, "com");								
								telosVal2=Long.parseLong(stz.nextToken());
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF88.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens89!="") {

					tF = new BufferedReader(new FileReader(pathTelos));
					xF89 = new BufferedReader(new FileReader(pathXsens89));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens489.txt");
					f=new File(path+"\\tXsens489.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF89.readLine();
						aux=xF89.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						calculFreq=true;
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF89.readLine();aux=xF89.readLine();aux=xF89.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, "com");

						telosVal1=Long.parseLong(stz.nextToken());							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, "com");								
								telosVal2=Long.parseLong(stz.nextToken());
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF89.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				if (pathXsens90!=""){

					tF = new BufferedReader(new FileReader(pathTelos));
					xF90 = new BufferedReader(new FileReader(pathXsens90));

					// et là, on va creer le fichoer ou on va fusionner les données Telos et Xsens.
					System.out.println(path+"\\tXsens490.txt");
					f=new File(path+"\\tXsens490.txt");
					f.createNewFile();
					fTX=new BufferedWriter(new FileWriter(f.getAbsolutePath()));


					try{


						//ici on calcule le frequence d'echantillonage et la periode d'echantillonage de capteurs Xsens.

						aux=xF90.readLine();
						aux=xF90.readLine();

						freqEchantillonageXsens=CalculerFrequencedechantillonage(aux);
						periodeEchantillonageXsens=1000/freqEchantillonageXsens; // *1000 (millisecond)
						calculFreq=true;
						System.out.println("Frequence d'échantillonage Xsens : "+freqEchantillonageXsens);
						System.out.println("Periode d'échantillonage Xsens : "+periodeEchantillonageXsens);

						aux=xF90.readLine();aux=xF90.readLine();aux=xF90.readLine(); 

						// ,Ici on calcule le temps initial de la collecte.
						aux=tF.readLine();

						StringTokenizer stz=new StringTokenizer(aux, "com");

						telosVal1=Long.parseLong(stz.nextToken());							

						while((line=tF.readLine() )!= null ){							

							aux=tF.readLine();

							if ((aux=tF.readLine() )!= null )
							{
								stz=new StringTokenizer(aux, "com");								
								telosVal2=Long.parseLong(stz.nextToken());
								diffEntreDeuxEchantillonTelos=telosVal2-telosVal1;


								compt=(int)(diffEntreDeuxEchantillonTelos/periodeEchantillonageXsens);
								// Ici on va remplir le fichier, pour stocker les données Telos et Xsens, qu'on a deja creer.


								for (int i = 0; i < compt; i++) {
									aux2=xF90.readLine();
									if (aux2==null) break;
									stz=new StringTokenizer(aux2, ";");
									aux2=stz.nextToken();aux=stz.nextToken();
									aux2="";
									while(stz.hasMoreTokens())
										aux2+=stz.nextToken()+" ";

									fTX.write(line+aux2+"\n");	

								}

								telosVal1=telosVal2;
							}
						}
						fTX.close();		

					} catch (IOException e) {
						fTX.close();
						e.printStackTrace();
					}	

				}

				///////////////////////////////////////////////////////////////////////:************* i am here !!!

				/*while(line != null)
		             {
		                    System.out.println(line);
		                    line = in.readLine();
		             }
				 */
				//	System.out.println(line);

			} catch (IOException e) {e.printStackTrace();}



		}
	}

	private void initialize() {

		cbCTimeMS.setSelected(true);

		frame = new JFrame();
		frame.setTitle("Fusion");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(0, 0, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Telos : ");
		lblNewLabel.setBounds(10, 176, 334, 14);



		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"*.txt","txt");
		chooserTelos.setFileFilter(filter);
		chooserXsens485.setFileFilter(filter);
		chooserXsens486.setFileFilter(filter);
		chooserXsens487.setFileFilter(filter);
		chooserXsens488.setFileFilter(filter);
		chooserXsens489.setFileFilter(filter);
		chooserXsens490.setFileFilter(filter);


		LNomTelos.setBounds(455, 176, 229, 14);
		frame.getContentPane().add(LNomTelos);


		lNomXsens85.setBounds(455, 205, 229, 14);
		frame.getContentPane().add(lNomXsens85);


		lNomXsens86.setBounds(455, 234, 229, 14);
		frame.getContentPane().add(lNomXsens86);


		lNomXsens87.setBounds(455, 263, 229, 14);
		frame.getContentPane().add(lNomXsens87);


		lNomXsens88.setBounds(455, 292, 229, 14);
		frame.getContentPane().add(lNomXsens88);


		lNomXsens89.setBounds(455, 321, 229, 14);
		frame.getContentPane().add(lNomXsens89);


		lNomXsens90.setBounds(455, 350, 229, 14);
		frame.getContentPane().add(lNomXsens90);

		frame.getContentPane().add(lblNewLabel);

		JButton bReinitialiser = new JButton("Reinitialiser");
		bReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				reinitialiser();
			}
		});
		bReinitialiser.setBounds(396, 427, 110, 23);
		frame.getContentPane().add(bReinitialiser);

		JButton bFermer = new JButton("Fermer");
		bFermer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				config.dispose();
			}
		});
		bFermer.setBounds(516, 427, 89, 23);
		frame.getContentPane().add(bFermer);

		JButton bTelos = new JButton("Ajouter");
		bTelos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserTelos.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					LNomTelos.setText(""+chooserTelos.getSelectedFile().getName());
					pathTelos=chooserTelos.getSelectedFile().getPath();
				}
			}
		});

		bTelos.setBounds(354, 167, 91, 23);
		frame.getContentPane().add(bTelos);



		JLabel lblSelectionnezLeFichier = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Xsens 485 : ");
		lblSelectionnezLeFichier.setBounds(10, 205, 334, 14);
		frame.getContentPane().add(lblSelectionnezLeFichier);

		JButton bXsens85 = new JButton("Ajouter");
		bXsens85.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserXsens485.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lNomXsens85.setText(""+chooserXsens485.getSelectedFile().getName());
					pathXsens85=chooserXsens485.getSelectedFile().getPath();
				}
			}
		});
		bXsens85.setBounds(354, 196, 91, 23);
		frame.getContentPane().add(bXsens85);



		JLabel lblSelectionnezLeFichier_1 = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Xsens 486 : ");
		lblSelectionnezLeFichier_1.setBounds(10, 234, 334, 14);
		frame.getContentPane().add(lblSelectionnezLeFichier_1);

		JButton bXsens86 = new JButton("Ajouter");
		bXsens86.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserXsens486.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lNomXsens86.setText(""+chooserXsens486.getSelectedFile().getName());
					pathXsens86=chooserXsens486.getSelectedFile().getPath();
				}
			}
		});
		bXsens86.setBounds(354, 225, 91, 23);
		frame.getContentPane().add(bXsens86);


		JLabel lblSelectionnezLeFichier_2 = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Xsens 487 : ");
		lblSelectionnezLeFichier_2.setBounds(10, 263, 334, 14);
		frame.getContentPane().add(lblSelectionnezLeFichier_2);

		JButton bXsens87 = new JButton("Ajouter");
		bXsens87.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserXsens487.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lNomXsens87.setText(""+chooserXsens487.getSelectedFile().getName());
					pathXsens87=chooserXsens487.getSelectedFile().getPath();
				}
			}
		});
		bXsens87.setBounds(354, 254, 91, 23);
		frame.getContentPane().add(bXsens87);



		JLabel lblSelectionnezLeFichier_3 = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Xsens 488 : ");
		lblSelectionnezLeFichier_3.setBounds(10, 292, 334, 14);
		frame.getContentPane().add(lblSelectionnezLeFichier_3);

		JButton bXsens88 = new JButton("Ajouter");
		bXsens88.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserXsens488.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lNomXsens88.setText(""+chooserXsens488.getSelectedFile().getName());
					pathXsens88=chooserXsens488.getSelectedFile().getPath();

				}
			}
		});
		bXsens88.setBounds(354, 283, 91, 23);
		frame.getContentPane().add(bXsens88);

		JLabel lblSelectionnezLeFichier_4 = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Xsens 489 : ");
		lblSelectionnezLeFichier_4.setBounds(10, 321, 334, 14);
		frame.getContentPane().add(lblSelectionnezLeFichier_4);

		JButton bXsens89 = new JButton("Ajouter");
		bXsens89.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserXsens489.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lNomXsens89.setText(""+chooserXsens489.getSelectedFile().getName());
					pathXsens89=chooserXsens489.getSelectedFile().getPath();
				}
			}
		});
		bXsens89.setBounds(354, 312, 91, 23);
		frame.getContentPane().add(bXsens89);

		JLabel lblSelectionnezLeFichier_5 = new JLabel("Selectionnez le fichier contenant les donn\u00E9es Xsens 490 : ");
		lblSelectionnezLeFichier_5.setBounds(10, 350, 334, 14);
		frame.getContentPane().add(lblSelectionnezLeFichier_5);

		JButton bXsens90 = new JButton("Ajouter");
		bXsens90.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooserXsens490.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lNomXsens90.setText(""+chooserXsens490.getSelectedFile().getName());
					pathXsens90=chooserXsens490.getSelectedFile().getPath();
				}
			}
		});
		bXsens90.setBounds(354, 341, 91, 23);
		frame.getContentPane().add(bXsens90);		

		JButton bFusionner = new JButton("Fusionner");
		bFusionner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// fusion 
				JFileChooser chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choisir un rep");//choosertitle
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//    
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { 			     

					fusionTous(""+chooser.getSelectedFile());
					/*
					if (cbCTimeMS.isSelected())
						fusionner(""+chooser.getSelectedFile());			
					else
						fusionnersecond(""+chooser.getSelectedFile());
					 */
					if(cbCReinit.isSelected()) reinitialiser();
				}
				else {
					System.out.println("No Selection ");
				}
			}
		});
		bFusionner.setBounds(286, 427, 100, 23);
		frame.getContentPane().add(bFusionner);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 684, 21);
		frame.getContentPane().add(menuBar);

		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);

		JMenuItem mntmFermer = new JMenuItem("Fermer");
		mnFichier.add(mntmFermer);
		mntmFermer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		JMenu mnConfig = new JMenu("Config");
		menuBar.add(mnConfig);

		JMenuItem mntmConfig = new JMenuItem("Config");
		mnConfig.add(mntmConfig);
		mntmConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cbCTimeMS.isSelected()) cbCTimeMSP.setSelected(true);
				else cbCTimeSP.setSelected(true);

				config.setVisible(true);
				config.setSize(400, 500);
				config.setTitle("Configuration");
				config.getContentPane().setBackground(Color.WHITE);				
				config.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				config.getContentPane().setLayout(null);



				JLabel lCTime=new JLabel("Format du temps : ");
				lCTime.setBounds(10, 180, 150, 15);
				//	config.getContentPane().add(lCTime);


				cbCTimeSP.setBounds(180, 180, 150, 20);
				cbCTimeSP.setBackground(Color.WHITE);
				//	config.getContentPane().add(cbCTimeSP);

				cbCTimeMSP.setBounds(180, 200, 150, 20);
				cbCTimeMSP.setBackground(Color.WHITE);
				//		config.getContentPane().add(cbCTimeMSP);

				JLabel lCReinit=new JLabel("Reinitialisation automatique : ");
				lCReinit.setBounds(10, 300, 180, 15);
				config.getContentPane().add(lCReinit);


				cbCReinitP.setBounds(180, 300, 20, 20);
				cbCReinitP.setBackground(Color.WHITE);
				config.getContentPane().add(cbCReinitP);

				cbCTimeSP.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {																
						cbCTimeMSP.setSelected(false);
						cbCTimeSP.setSelected(true);
					}
				});

				cbCTimeMSP.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						cbCTimeSP.setSelected(false);
						cbCTimeMSP.setSelected(true);

					}
				});

				cbCReinit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (cbCTimeMS.isSelected()) bReinitialiser.setEnabled(false);
						else bReinitialiser.setEnabled(true);
					}
				});
				JButton save=new JButton("Enregistrer");
				save.setBounds(150, 400, 100, 25);				
				config.getContentPane().add(save);
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (cbCTimeMSP.isSelected())  {cbCTimeMS.setSelected(true);cbCTimeS.setSelected(false);}
						else {cbCTimeS.setSelected(true);cbCTimeMS.setSelected(false);}
						if(cbCReinitP.isSelected()){
							cbCReinit.setSelected(true);
							bReinitialiser.setEnabled(false);
						}
						else {
							cbCReinit.setSelected(false);
							bReinitialiser.setEnabled(true);
						}
						config.dispose();
					}
				});
				JButton fermer=new JButton("Fermer");
				fermer.setBounds(255, 400, 100, 25);				
				config.getContentPane().add(fermer);

				////////////*********************************************************************************************


				if (cbCReinit.isSelected()) cbCReinitP.setSelected(true);

				fermer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						config.dispose();						
					}
				});
			}
		});
		JMenu mnAPropos = new JMenu("A propos");
		menuBar.add(mnAPropos);

		JMenuItem mntmInfo = new JMenuItem("Info");
		mnAPropos.add(mntmInfo);

		JFrame info=new JFrame();	
		info.setSize(500, 400);
		info.setTitle("Info");
		info.getContentPane().setBackground(Color.WHITE);				
		info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		info.getContentPane().setLayout(null);
		
		JTextPane lblText = new JTextPane();
		lblText.setCaretColor(Color.gray);
		lblText.setText(""
				+ "  - Vous telechargez les fichiers de données en cliquant sur les bouttons 'Ajouter'.\n"
				+ "  - Cliquez sur 'Fusionner'.\n"
				+ "  - Puis choisissez le dossier ou vous voulez sauvegarder le fichier.\n");
		lblText.setBounds(20, 20, 400, 200);
		info.getContentPane().add(lblText);
		
		JButton fermerInfo=new JButton("OK");
		fermerInfo.setBounds(300, 275, 100, 25);
		info.getContentPane().add(fermerInfo);
		fermerInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				info.setVisible(false);
			
			}
		});
		

		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setVisible(true);
			}
		});
		
		
		
		JMenuItem mntmAPropos = new JMenuItem("A propos");
		mnAPropos.add(mntmAPropos);
		
////////////////////////////////////////////::
		
		

		JFrame aPropos=new JFrame();	
		aPropos.setSize(500, 400);
		aPropos.setTitle("A propos");
		aPropos.getContentPane().setBackground(Color.WHITE);				
		aPropos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aPropos.getContentPane().setLayout(null);
		
		JTextPane lblTextAPropos = new JTextPane();
		lblTextAPropos.setCaretColor(Color.gray);
		lblTextAPropos.setText("\n\n\n\n "
				+ "__________________________________________\n"
				+ "        Développé par : .\n\n"
				+ "               - Aymen LABIADH.\n"
				+ "               - aymen@labiadh.fr.\n");
		lblTextAPropos.setBounds(20, 20, 400, 200);
		aPropos.getContentPane().add(lblTextAPropos);
		
		JButton fermeraPropos=new JButton("OK");
		fermeraPropos.setBounds(300, 275, 100, 25);
		aPropos.getContentPane().add(fermeraPropos);
		fermeraPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				aPropos.setVisible(false);
			
			}
		});
		

		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setVisible(true);
			}
		});

		mntmAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aPropos.setVisible(true);
			}
		});
		
		///////////////////////////////////////////////:
		JLabel label = new JLabel("");
		Image img=new ImageIcon(this.getClass().getResource("/UPEC-logo.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(10, 23, 450, 133);
		frame.getContentPane().add(label);
	}
}
