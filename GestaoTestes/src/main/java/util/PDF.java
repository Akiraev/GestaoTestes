package util;

import java.awt.Graphics2D;
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
import java.text.SimpleDateFormat;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import dao.DaoProjeto;
import dominio.Historico;
import dominio.Projeto;
import dominio.Usuario;

public class PDF {
	private static Document documento;

	public static boolean relatorioUsuario(Usuario usuario) {

		documento = new Document();

		try {

			String path = new File(".").getCanonicalPath();
			PdfWriter.getInstance(documento, new FileOutputStream(path + "/" + usuario.getNomeUsuario() + ".pdf"));
			documento.open();

			// adicionando um parágrafo no documento
			documento.add(new Paragraph("Gerando PDF - Java"));
			documento.close();

			// PDF.downloadPDF(usuario.getNomeUsuario() + ".PDF", path);
			return true;

		} catch (DocumentException de) {
			Mensagem.falha(de.getMessage());
		} catch (IOException ioe) {
			Mensagem.falha(ioe.getMessage());
		}
		documento.close();
		return false;

	}

	public static boolean relatorioHistorico(Projeto projeto) {
		documento = new Document();
		try {

			String path = new File(".").getCanonicalPath();
			PdfWriter writer = PdfWriter.getInstance(documento,
					new FileOutputStream(path + "/" + projeto.getNomeProjeto() + ".pdf"));
			documento.open();

			documento.setPageSize(PageSize.A3);
			// adicionando um parágrafo no documento
			documento.add(new Paragraph("Relatório de projeto/histórico"));
			documento.add(new Paragraph(". "));

			/*PdfContentByte cb = writer.getDirectContent();
			float width = PageSize.A4.getWidth();
			float height = PageSize.A4.getHeight() / 2;
			// Pie chart
			PdfTemplate pie = cb.createTemplate(width, height);
			Graphics2D g2d1 = new PdfGraphics2D(pie, width, height);
			Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, width, height);
			getPieChart(projeto).draw(g2d1, r2d1);
			g2d1.dispose();
			cb.addTemplate(pie, 0, height);*/
			
			documento.add(tabelaHistorico(projeto));
			documento.close();

			return true;

		} catch (DocumentException de) {
			Mensagem.falha(de.getMessage());
		} catch (IOException ioe) {
			Mensagem.falha(ioe.getMessage());
		} finally {
			documento.close();
		}
		return false;

	}

	private static void downloadPDF(String nomeArquivo, String localDoArquivo) throws IOException {

		int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
		File file = null;

		try {
			String nomePDF = nomeArquivo.replaceAll(" ", "_");
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

	public static PdfPTable tabelaHistorico(Projeto projeto) {
		Paragraph tableHead = new Paragraph(projeto.getNomeProjeto());
		tableHead.setAlignment(Element.ALIGN_CENTER);
		PdfPTable table = new PdfPTable(5);
		PdfPCell header = new PdfPCell(tableHead);
		header.setColspan(5);
		table.addCell(header);
		table.addCell("Realização");
		table.addCell("Ação");
		table.addCell("Historico");
		table.addCell("Responsável");
		table.addCell("Status");
		projeto = DaoProjeto.buscaProjetoLazy(projeto);
		for (Historico h : projeto.getHistoricos()) {
			table.addCell(new SimpleDateFormat("dd/MM/yyyy").format(h.getDataRealizacao()));
			table.addCell(h.getTipoAcao().getTipoAcao());
			table.addCell(h.getAcaoHistorico().getAcaoHistorico());
			table.addCell(h.getResponsavel().getNomeUsuario());
			table.addCell(h.getStatusHistorico().getStatusHistorico());
		}
		return table;
	}

	/*public static JFreeChart getPieChart(Projeto projeto) throws IOException {
		projeto = DaoProjeto.buscaProjetoLazy(projeto);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Historico h : projeto.getHistoricos()) {
			dataset.setValue("");
		}
		return ChartFactory.createPieChart("Movies / directors", dataset, true, true, false);
	}*/

}
