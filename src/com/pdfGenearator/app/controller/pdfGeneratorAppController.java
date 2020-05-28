package com.pdfGenearator.app.controller;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdfGenearator.app.Main;
import com.pdfGenearator.app.test.generatePDFApp;
import com.pdfGenearator.app.test.*;



@RestController

public class pdfGeneratorAppController {
	
	@Autowired generatePDFApp GeneratePDFApp;
	
	@RequestMapping(value = "/collectPDF")
	public String generate() throws IOException
	{
		GeneratePDFApp.generatePDF();
		
		return "pdf generated!! success";
	}

}
