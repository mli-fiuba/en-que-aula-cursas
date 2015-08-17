package com.mlifiuba.dondecurso.api;

import java.util.List;

public class DetailsModel {

	private String docentes;
	private String vacantes;
	private String carreras;
	private List<Horario> horarios;

	public String getDocentes() {
		return docentes;
	}

	public void setDocentes(String docentes) {
		this.docentes = docentes;
	}

	public String getVacantes() {
		return vacantes;
	}

	public void setVacantes(String vacantes) {
		this.vacantes = vacantes;
	}

	public String getCarreras() {
		return carreras;
	}

	public void setCarreras(String carreras) {
		this.carreras = carreras;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	@Override
	public String toString() {
		return docentes + horarios.toString();
	}
}
