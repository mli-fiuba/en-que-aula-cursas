package com.mlifiuba.dondecurso.api;

import java.util.List;

public class GetDepartamentosStrategy implements InformationStrategy{

	private static final long serialVersionUID = 4223483743241037282L;

	@Override
	public List<InformationModel> getInfo(String selection) {
		InformationClient client = new InformationClient();
		List<InformationModel> deptos = client.getDeptos();
		deptos.remove(deptos.size() - 1);
		return deptos;
	}

	@Override
	public String getLabel() {
		return "Obteniendo Departamentos";
	}
}
