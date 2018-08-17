package bean;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import dao.DaoProjeto;
import dominio.CheckPoint;
import dominio.Historico;
import dominio.Projeto;
import dominio.Usuario;
import enumeradores.AtributesSession;
import enumeradores.DireitoUsuario;
import enumeradores.Periodo;
import factory.SessionContextFactory;

@ManagedBean(name = "dashboardBean")
@ViewScoped
public class DashboardBean {
	
	private Periodo periodoProjeto;
	private Periodo periodoHistorico;
	private List<Projeto> projetos;
	private List<Historico> historicos;

	@PostConstruct
	public void init() {
		
		List<Projeto> ps = DaoProjeto.listarProjetosAtivos();
		Usuario usuarioLogado = (Usuario) SessionContextFactory.getInstance()
				.getAttribute(AtributesSession.USUARIO_LOGADO.getValue());
		this.projetos = atribuiProjeto(ps, usuarioLogado);
		this.historicos = atribuiHistorico(ps, usuarioLogado);
	}

	public List<Projeto> atribuiProjeto(List<Projeto> ps, Usuario usuarioLogado) {
		
		List<Projeto> projetos = new ArrayList<Projeto>();

		if (usuarioLogado != null && usuarioLogado.getDireitoUsuario().equals(DireitoUsuario.ANALISTA)) {
			for (Projeto p : ps) {
				if (p.getAnalista().getCodUsuario().equals(usuarioLogado.getCodUsuario())) {
					calculaProgresso(p);
					projetos.add(p);
				}
			}
		} else {
			for (Projeto p : ps) {
				calculaProgresso(p);
				projetos.add(p);
			}
		}
		return projetos;
	}

	public List<Historico> atribuiHistorico(List<Projeto> ps, Usuario usuarioLogado) {
		
		List<Historico> historicos = new ArrayList<Historico>();

		if (usuarioLogado != null) {
			for (Projeto p : ps) {
				p = DaoProjeto.buscaProjetoLazy(p);
				for (Historico h : p.getHistoricos()) {
					if (h.getResponsavel().getCodUsuario().equals(usuarioLogado.getCodUsuario()))
						historicos.add(h);
				}
			}

		} else {
			
			for (Projeto p : ps) {
				
				p = DaoProjeto.buscaProjetoLazy(p);
				for (Historico h : p.getHistoricos()) {
					historicos.add(h);
				}
			}
		}
		return historicos;
	}

	public void filtrarProjetoPorData() {
		
		if (this.periodoProjeto != null) {
			List<Projeto> ps = DaoProjeto.listarProjetosAtivos();
			Usuario usuarioLogado = (Usuario) SessionContextFactory.getInstance()
					.getAttribute(AtributesSession.USUARIO_LOGADO.getValue());
			ps = atribuiProjeto(ps, usuarioLogado);

			List<Projeto> pro = new ArrayList<>();
			Date hoje = new Date();
			Calendar calendar = Calendar.getInstance();

			Calendar calendarOntem = Calendar.getInstance();
			calendarOntem.setTime(hoje);
			calendarOntem.set(Calendar.DAY_OF_MONTH, calendarOntem.get(Calendar.DAY_OF_MONTH) - 1);
			Date ontem = calendarOntem.getTime();

			switch (this.periodoProjeto) {
			case DIA:
				calendar.setTime(hoje);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
				String umDiaString = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

				for (Projeto p : ps) {
					String dataFimProjeto = new SimpleDateFormat("dd/MM/yyyy").format(p.getDataProjetoFim());
					if (dataFimProjeto.equals(umDiaString)) {
						calculaProgresso(p);
						pro.add(p);
					}
				}
				this.projetos = pro;
				break;

			case SEMANA:
				calendar.setTime(hoje);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 8);
				Date umaSemana = calendar.getTime();

				for (Projeto p : ps) {
					if (p.getDataProjetoFim().after(ontem) && p.getDataProjetoFim().before(umaSemana)) {
						calculaProgresso(p);
						pro.add(p);
					}
				}
				this.projetos = pro;
				break;

			case MES:
				calendar.setTime(hoje);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				Date umMes = calendar.getTime();

				for (Projeto p : ps) {
					if (p.getDataProjetoFim().after(ontem) && p.getDataProjetoFim().before(umMes)) {
						calculaProgresso(p);
						pro.add(p);
					}
				}
				this.projetos = pro;
				break;

			case TODO_PERIODO:
				this.projetos = atribuiProjeto(ps, usuarioLogado);
				break;

			default:
				break;

			}
		}

	}

	public void filtrarHistoricoPorData() {
		if (this.periodoHistorico != null) {
			List<Projeto> ps = DaoProjeto.listarProjetosAtivos();
			Usuario usuarioLogado = (Usuario) SessionContextFactory.getInstance()
					.getAttribute(AtributesSession.USUARIO_LOGADO.getValue());
			ps = atribuiProjeto(ps, usuarioLogado);

			List<Historico> hs = new ArrayList<>();
			Date hoje = new Date();
			Calendar calendar = Calendar.getInstance();

			Calendar calendarOntem = Calendar.getInstance();
			calendarOntem.setTime(hoje);
			calendarOntem.set(Calendar.DAY_OF_MONTH, calendarOntem.get(Calendar.DAY_OF_MONTH) - 1);
			Date ontem = calendarOntem.getTime();

			switch (this.periodoHistorico) {
			case DIA:
				calendar.setTime(hoje);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
				String umDiaString = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

				for (Projeto p : ps) {
					p = DaoProjeto.buscaProjetoLazy(p);
					for (Historico h : p.getHistoricos()) {
						String dataFimHistorico = new SimpleDateFormat("dd/MM/yyyy").format(h.getDataFechamento());
						if (dataFimHistorico.equals(umDiaString)) {
							calculaProgresso(p);
							hs.add(h);
						}
					}
				}
				this.historicos = hs;
				break;

			case SEMANA:
				calendar.setTime(hoje);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 8);
				Date umaSemana = calendar.getTime();

				for (Projeto p : ps) {
					p = DaoProjeto.buscaProjetoLazy(p);
					for (Historico h : p.getHistoricos()) {
						if (h.getDataFechamento().after(ontem) && h.getDataFechamento().before(umaSemana)) {
							calculaProgresso(p);
							hs.add(h);
						}
					}
				}
				this.historicos = hs;
				break;

			case MES:
				calendar.setTime(hoje);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				Date umMes = calendar.getTime();

				for (Projeto p : ps) {
					p = DaoProjeto.buscaProjetoLazy(p);
					for (Historico h : p.getHistoricos()) {
						if (h.getDataFechamento().after(ontem) && h.getDataFechamento().before(umMes)) {
							calculaProgresso(p);
							hs.add(h);
						}
					}
				}
				this.historicos = hs;
				break;

			case TODO_PERIODO:
				this.historicos = atribuiHistorico(ps, usuarioLogado);
				break;

			default:
				break;

			}
		}

	}

	public void calculaProgresso(Projeto projeto) {
		double progresso = 0;
		double concluido = 0;

		if (projeto.getCheckPoint() == null) {
			projeto.setCheckPoint(new CheckPoint());
		}

		if (projeto.getCheckPoint().isColetaLogs())
			concluido += 1;
		if (projeto.getCheckPoint().isCapacityPlanner())
			concluido += 1;
		if (projeto.getCheckPoint().isEntregaRelatório())
			concluido += 1;
		if (projeto.getCheckPoint().isInstrumentacao())
			concluido += 1;
		if (projeto.getCheckPoint().isLevantamentoRequisitos())
			concluido += 1;
		if (projeto.getCheckPoint().isPerformance())
			concluido += 1;
		if (projeto.getCheckPoint().isReuniaoEntendimento())
			concluido += 1;
		if (projeto.getCheckPoint().isStresTest())
			concluido += 1;

		if (concluido == 0) {
			projeto.getCheckPoint().setProgresso(0);
		} else {
			progresso = (concluido * 100) / 8;
			projeto.getCheckPoint().setProgresso(progresso);
		}

	}

	public Periodo[] getPeriodo() {
		return Periodo.values();
	}

	public Periodo getPeriodoProjeto() {
		return periodoProjeto;
	}

	public void setPeriodoProjeto(Periodo periodoProjeto) {
		this.periodoProjeto = periodoProjeto;
	}

	public Periodo getPeriodoHistorico() {
		return periodoHistorico;
	}

	public void setPeriodoHistorico(Periodo periodoHistorico) {
		this.periodoHistorico = periodoHistorico;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}
	
	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage(null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnSelect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage(null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
}
