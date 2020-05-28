package com.pdfGenearator.app.test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;

@Component
public class generatePDFApp {
	
	
	
	public void generatePDF() throws IOException{

		
        String fileName = "PdfWithtext.pdf"; 
        
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();

        doc.addPage(page);
        
        

        PDPageContentStream content = new PDPageContentStream(doc, page);
        
        //line cordinates (xStart, yStart, xEnd, yEnd);
        content.drawLine(0, 720, 800, 720); 
        content.drawLine(0, 40, 800, 40);   
       
        
        //-----------------------adding text------------------------------------------
        
        content.beginText(); 
        
        //Setting the font to the Content stream
        content.setFont( PDType1Font.TIMES_ROMAN, 9 );
         
        //Setting the leading
        content.setLeading(14.5f);

        //Setting the position for the line
        content.newLineAtOffset(25, 700);

        String texta = "Greetings from Amazon Web Services,";
        String textb = "We're writing to provide you with an electronic invoice for your use of AWS services.Your account will be charged $XXX.XX. Additional  ";
        String textc = "information regarding your bill, individual service charge details, and your account history are available on the Account Summary Page.";

        //Adding text in the form of string
        content.showText(texta);
        content.newLine();
        content.showText(textb);
        content.newLine();
        content.showText(textc);
        content.newLine();
       
        
        //Ending the content stream
        content.endText();
        
        //----------------------ADDING TABLEs-------------------------------------
        //Dummy Table
        float margin = 50;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        //float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
       // float yPosition = 550;

        //--------------------------------Summary TABLE ------------------------------------------

        BaseTable summarytable = new BaseTable(550, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);
        
       
        Row<PDPage> headerRow = summarytable.createRow(15f);
        Cell<PDPage> cell1 = headerRow.createCell(100, "Summary");
        summarytable.addHeaderRow(headerRow);


        Row<PDPage> row = summarytable.createRow(12);
        cell1 = row.createCell(50, "Account ID:");
        cell1 = row.createCell(50, "Invoice Date:"+LocalDate.now());
        
        summarytable.draw();
        
       // --------------------------------Services Usage Table-------------------------------------------
        
        ArrayList<Integer> amountList = new ArrayList<Integer>();
        amountList.add(14);
        amountList.add(7);
        amountList.add(39);
        amountList.add(40);
        
        ArrayList<String> servicesList = new ArrayList<String>();
        servicesList.add("--services name--");
        servicesList.add("--services name--");
        servicesList.add("--services name--");
        servicesList.add("--services name--");
        
        
        BaseTable servicesTable = new BaseTable(450, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);

        Row<PDPage> headerRow2 = servicesTable.createRow(15f);
        Cell<PDPage> cell2 = headerRow2.createCell(100, "Blling Period:");
        servicesTable.addHeaderRow(headerRow2);


        Row<PDPage> row2 = servicesTable.createRow(12);
        cell2 = row2.createCell(50, "Service Name:");
        cell2 = row2.createCell(50, "Amount Due");

        int sum_amount = 0;
        for(int i=0; i<servicesList.size(); i++)
        {
             Row<PDPage> row3 = servicesTable.createRow(12);
             cell2 = row3.createCell(50,servicesList.get(i).toString());
             cell2 = row3.createCell(50,amountList.get(i).toString());
             
              sum_amount  = sum_amount + amountList.get(i);
        }
        
        
        Row<PDPage> row6 = servicesTable.createRow(12);
        cell2 = row6.createCell(50, "Total Due:");
        cell2 = row6.createCell(50, "" + sum_amount);
        
        
        int spaceBetweenTables = 50;
    	
		// we want 2 tables so our table width is 50% of page width without left and right margin AND provided space between tables
		float tableWidth1 = 0.5f * (page.getMediaBox().getWidth() - (2 * 25)- spaceBetweenTables);
		
		
		 servicesTable.draw();
        // --------------------------------TABLE Bill to (Customer name)-------------------------------------------

        BaseTable customerTable = new BaseTable(650, yStartNewPage, bottomMargin, tableWidth1, margin, doc, page, true, drawContent);
        
        

        Row<PDPage> headerRow3 = customerTable.createRow(15f);
        Cell<PDPage> cell3 = headerRow3.createCell(90, "Bill  To:");
        customerTable.addHeaderRow(headerRow3);

        Row<PDPage> row7 = customerTable.createRow(12);
        cell3 = row7.createCell(90, "---Cutomer Name---");
        
        customerTable.draw();
        // --------------------------------TABLE Service Provider-------------------------------------------

        BaseTable ser_providerTable = new BaseTable(650, yStartNewPage, bottomMargin, tableWidth1, 25 + tableWidth1 + spaceBetweenTables, doc, page, true, drawContent);
        
        

        Row<PDPage> headerRow4 = ser_providerTable.createRow(15f);
        Cell<PDPage> cell4 = headerRow4.createCell(90, "Service Provider");
        ser_providerTable.addHeaderRow(headerRow4);


        Row<PDPage> row8 = ser_providerTable.createRow(12);
        cell4 = row8.createCell(90, "The Amazon Web Services LLC");
       

        
        ser_providerTable.draw();
        
        //----------------------ADDING IMAGE---------------------------------------
        
        //Logo path
        
        PDImageXObject pdImage = PDImageXObject.createFromFile("C:/Users/saura/Desktop/aws_logo.png",doc);
        //						X ,Y,HEIGHT, WIDTH
        content.drawImage(pdImage, 520, 730,80,80);
       
        //----------------------ADDING TEXT---------------------------------------

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(235, 750);
        content.drawString("Billing Invoice");
        content.endText();
        
       //Begin the Content stream 
        content.beginText(); 
         
        //Setting the font to the Content stream
        content.setFont( PDType1Font.TIMES_ROMAN, 9 );
         
        //Setting the leading
        content.setLeading(14.5f);

        //Setting the position for the line
        content.newLineAtOffset(25, 300);

        String text1 = "This in not VAT inc";
        String text2 = "All web services are sold by Amazon Web Services LLC";
        String text3 = "The above charges include charges incurred by your account as well as by all accounts you are responsible for through Consolidated Billing.";
        String text4 = "For customers who need to remit consumption tax in Japan, the Account Summary Page provides details of services Japan.";
        
        String text8 = 	"This message was producedand distributed by Amazon Web Services LLC, 410 Terry Avenue";
        String text9 = "North, Seattle, Washington 98109-5210";

        //Adding text in the form of string
        content.showText(text1);
        content.newLine();
        content.showText(text2);
        content.newLine();
        content.showText(text3);
        content.newLine();
        content.newLine();
        content.showText(text4);
        content.newLine();
        content.newLine();
        content.showText(text8);
        content.newLine();
        content.showText(text9);
        
        //Ending the content stream
        content.endText();
        
        //---------------------adding text----------------------
        
        content.beginText(); 
        
        //Setting the font to the Content stream
        content.setFont( PDType1Font.TIMES_ROMAN, 9 );
         
        //Setting the leading
        content.setLeading(14.5f);

        //Setting the position for the line
        content.newLineAtOffset(350, 100);
        
        String text5 = "Thank you for using Amazon Web Services..";
        String text6 = "Sincerely,";
        String text7 = "The Amazon Web Services Team";

        content.showText(text5);
        content.newLine();
        content.showText(text6);
        content.newLine();
        content.showText(text7);
        
        content.endText();
    
        
        //--------------------------------------------------------------------------
        
        //closing conten stream object
 
        content.close();
        doc.save(fileName);
        doc.close();
        
        System.out.println("  Your file created in : "+ System.getProperty("user.dir"));
		
	}

}
