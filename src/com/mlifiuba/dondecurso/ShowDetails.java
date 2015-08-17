package com.mlifiuba.dondecurso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mlifiuba.dondecurso.api.DetailsModel;
import com.mlifiuba.dondecurso.api.Horario;
import com.mlifiuba.dondecurso.api.InformationClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_details);
		String codigo = getIntent().getStringExtra(Index.SELECTED_ITEM);
		String nombre = getIntent().getStringExtra(Index.SELECTED_DESCRIPTION);
		setTitle(codigo + " - " + nombre);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ListView coursesListView;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
			coursesListView = (ListView) rootView.findViewById(R.id.coursesList);
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			ArrayAdapter<DetailsModel> adapter = new MyAdapter(getActivity(), android.R.layout.simple_list_item_1,
					new ArrayList<DetailsModel>());
			coursesListView.setAdapter(adapter);

			new AddStringTask(adapter, getActivity()).execute();
		}
	}

	public static class MyAdapter extends ArrayAdapter<DetailsModel> {

		public MyAdapter(Context context, int resource, List<DetailsModel> objects) {
			super(context, resource, objects);
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.details_row, parent, false);

			DetailsModel item = getItem(position);
			if (item != null) {
				TextView textView = (TextView) rowView.findViewById(R.id.profesor);
				textView.setText(item.getDocentes());
				TextView horariosListView = (TextView) rowView.findViewById(R.id.horarios_text);
				List<Horario> horarios = item.getHorarios();
				StringBuilder builder = new StringBuilder();
				for (Horario horario : horarios) {
					String aula = horario.getAula();
					if (aula != null) {
						builder.append(horario.getDia() + " de " + horario.getDesde() + " a " + horario.getHasta()
								+ ". Aula " + aula + ". " + horario.getTipo()).append("\n");
					}
				}
				horariosListView.setText(builder.toString());
			}
			return rowView;
		}
	}

	public static class HorariosAdapter extends ArrayAdapter<Horario> {

		public HorariosAdapter(Context context, int resource, List<Horario> objects) {
			super(context, resource, objects);
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.horarios_details, parent, false);

			Horario item = getItem(position);
			if (item != null) {
				TextView textView = (TextView) rowView.findViewById(R.id.horario_s);
				textView.setText("Aula " + item.getAula() + ". " + item.getDia() + " de " + item.getDesde() + " hasta "
						+ item.getHasta());

			}
			return rowView;
		}
	}

	public static class AddStringTask extends AsyncTask<Void, Void, Collection<DetailsModel>> {

		private ProgressDialog dialog;
		private Activity activity;
		private ArrayAdapter<DetailsModel> adapter;

		public AddStringTask(ArrayAdapter<DetailsModel> adapter, Activity activity) {
			super();
			this.adapter = adapter;
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(activity);
			dialog.setMessage("Cargando Materia");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Collection<DetailsModel> doInBackground(Void... unused) {
			String target = activity.getIntent().getStringExtra(Index.SELECTED_ITEM);
			Collection<DetailsModel> deptos = InformationClient.getCursos(target);
			return deptos;
		}

		@Override
		protected void onPostExecute(Collection<DetailsModel> result) {
			for (DetailsModel detailsModel : result) {
				adapter.add(detailsModel);
			}
			dialog.dismiss();
		}
	}
}
