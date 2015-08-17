package com.mlifiuba.dondecurso.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.mlifiuba.dondecurso.web.client.HttpClient;

public class InformationClient {

	private static final Gson gson = new Gson();

	private static final String BASE_URL = "http://www.mli-fiuba.com.ar/eqac/v2.0/?";
	private static final String VERSION = "v=1.0";

	private static final String DEPARTMENT_ID = "ds=1";
	private static final String SUBJECT_ID = "d=";
	private static final String COURSES_ID = "m=";
	private static final String SEPARATOR = "&";

	private static final String GET_DEPARTMENTS_URL = BASE_URL + VERSION + SEPARATOR + DEPARTMENT_ID;
	private static final String GET_SUBJECTS_URL = BASE_URL + VERSION + SEPARATOR + SUBJECT_ID;
	private static final String GET_COURSES_URL = BASE_URL + VERSION + SEPARATOR + COURSES_ID;

	public static List<InformationModel> getDeptos() {
		DeptosResponse fromJson = gson.fromJson(HttpClient.get(GET_DEPARTMENTS_URL), DeptosResponse.class);
		return fromJson.getCarreras();
	}

	public static List<InformationModel> getMaterias(String depto) {
		MateriasResponse fromJson = gson.fromJson(HttpClient.get(GET_SUBJECTS_URL + depto), MateriasResponse.class);
		return fromJson.getMaterias();
	}

	public static List<DetailsModel> getCursos(String materia) {
		return createDetails(gson.fromJson(HttpClient.get(GET_COURSES_URL + materia), Map.class));
	}

	private static List<DetailsModel> createDetails(Map fromJson) {
		List<DetailsModel> response = new ArrayList<DetailsModel>();
		if (fromJson == null) {
			return response;
		}

		Collection<Map> values = ((Map) fromJson.get("cursos")).values();
		for (Map value : values) {
			if (value == null) {
				continue;
			}

			DetailsModel details = new DetailsModel();
			details.setCarreras((String) value.get("carreras"));
			details.setDocentes((String) value.get("docentes"));

			List<Horario> horarios = new ArrayList<Horario>();
			List<Map> horariosMap = (List<Map>) value.get("horarios");
			for (Map horarioMap : horariosMap) {
				if (horarioMap == null) {
					continue;
				}
				Horario horario = new Horario();
				horario.setAula((String) horarioMap.get("aula"));
				horario.setDia((String) horarioMap.get("dia"));
				horario.setDesde((String) horarioMap.get("desde"));
				horario.setHasta((String) horarioMap.get("hasta"));
				horario.setTipo((String) horarioMap.get("tipo"));
				horarios.add(horario);
			}

			details.setHorarios(horarios);
			response.add(details);
		}

		return response;
	}

	private static class MateriasResponse {

		private List<InformationModel> materias;

		public List<InformationModel> getMaterias() {
			return materias;
		}

		public void setMaterias(List<InformationModel> materias) {
			this.materias = materias;
		}
	}

	private static class DeptosResponse {

		private List<InformationModel> carreras;

		public List<InformationModel> getCarreras() {
			return carreras;
		}

		public void setCarreras(List<InformationModel> carreras) {
			this.carreras = carreras;
		}
	}
}
