package com.mlifiuba.dondecurso.api;

import java.util.List;

public class GetMateriasStrategy implements InformationStrategy {

	private static final long serialVersionUID = 5074630051390697080L;

	@Override
	public List<InformationModel> getInfo(String selection) {
		InformationClient client = new InformationClient();
		List<InformationModel> deptos = client.getMaterias(selection);
		deptos.remove(deptos.size() - 1);
		return deptos;
	}

	@Override
	public String getLabel() {
		return "Obteniendo Materias";
	}
}
