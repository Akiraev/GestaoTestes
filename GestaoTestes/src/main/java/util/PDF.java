package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.Usuario;

public class PDF {

	public static boolean usuario(Usuario usuario) {

		Document documento = new Document();

		try {

			String path = new File(".").getCanonicalPath();
			PdfWriter.getInstance(documento, new FileOutputStream(path + "/" + usuario.getNomeUsuario() + ".pdf"));
			documento.open();

			// adicionando um parágrafo no documento
			documento.add(new Paragraph("Gerando PDF - Java"));
			documento.close();

			PDF.downloadPDF(usuario.getNomeUsuario() + ".PDF", path);
			return true;

		} catch (DocumentException de) {
			Mensagem.falha(de.getMessage());
		} catch (IOException ioe) {
			Mensagem.falha(ioe.getMessage());
		}
		documento.close();
		return false;

	}

	private static void downloadPDF(String nomeArquivo, String localDoArquivo) throws IOException {

		int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
		File file = null;

		try {
			String nomePDF = nomeArquivo.replaceAll(" ","_");
			file = new File(new URI(localDoArquivo + "/" + nomePDF));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=" + nomeArquivo);
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			output.flush();
		} catch (FileNotFoundException e) {
			// response.sendRedirect("./sistema/error/404.faces");
		} catch (Exception e) {
			// response.sendRedirect("./sistema/error/505.faces");
		} finally {
			close(output);
			close(input);
		}

		facesContext.responseComplete();
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
