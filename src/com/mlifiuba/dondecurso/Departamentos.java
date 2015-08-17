package com.mlifiuba.dondecurso;

import java.util.ArrayList;
import java.util.List;

import com.mlifiuba.dondecurso.api.InformationClient;
import com.mlifiuba.dondecurso.api.InformationModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Departamentos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_departamentos);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.departamentos, menu);
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

	public static class PlaceholderFragment extends Fragment {

		private ListView lv;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_departamentos, container, false);
			lv = (ListView) rootView.findViewById(R.id.departmentList);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					InformationModel itemAtPosition = (InformationModel) arg0.getItemAtPosition(arg2);
					Intent intent = new Intent(getActivity(), Materias.class);
					intent.putExtra(Index.SELECTED_ITEM, itemAtPosition.getCodigo());
					startActivity(intent);
				}
			});

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			ArrayAdapter<InformationModel> adapter = new MyAdapter(getActivity(), android.R.layout.simple_list_item_1,
					new ArrayList<InformationModel>());
			lv.setAdapter(adapter);

			new AddStringTask(adapter, getActivity()).execute();
		}
	}

	public static class MyAdapter extends ArrayAdapter<InformationModel> {

		public MyAdapter(Context context, int resource, List<InformationModel> objects) {
			super(context, resource, objects);
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.department_row, parent, false);

			InformationModel item = getItem(position);
			if (item != null) {
				TextView textView = (TextView) rowView.findViewById(R.id.cod);
				textView.setText(item.getCodigo());
				TextView nameView = (TextView) rowView.findViewById(R.id.nombre);
				nameView.setText(item.getNombre());
			}
			return rowView;
		}
	}

	public static class AddStringTask extends AsyncTask<Void, Void, List<InformationModel>> {

		private ArrayAdapter<InformationModel> adapter;
		private ProgressDialog dialog;
		private Activity activity;

		public AddStringTask(ArrayAdapter<InformationModel> adapter, Activity activity) {
			super();
			this.adapter = adapter;
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(activity);
			dialog.setMessage("Cargando Departamentos");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected List<InformationModel> doInBackground(Void... unused) {
			return InformationClient.getDeptos();
		}

		@Override
		protected void onPostExecute(List<InformationModel> result) {
			// Quito el último elemento, que es vacío.
//			result.remove(result.size() - 1);
			
			for (InformationModel model : result) {
				adapter.add(model);
			}

			dialog.dismiss();
		}
	}
}
