package com.kabricks.ster.demotiv.lists;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kabricks.ster.demotiv.MyDBHelper;
import com.kabricks.ster.demotiv.R;

import java.util.ArrayList;

public class PersonalFragment extends Fragment {


    public PersonalFragment() {
        // Required empty public constructor
    }

    ArrayList<DataModel> dataModels;
    ListView listView;
    ProgressBar progress;
    EditText editText;
    String userInput;
    View view;
    int selected_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_personal, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);

        dataModels = new ArrayList<>();

        //convert cursor here

        MyDBHelper db = new MyDBHelper(getContext());
        dataModels = db.getPersonalList();

        //dataModels.add(new DataModel("Apple Pie", false, 0));

        CustomAdapterPersonal adapter = new CustomAdapterPersonal(dataModels, getContext(), PersonalFragment.this);

        listView.setAdapter(adapter);

        setHasOptionsMenu(true);

        initProgressBar();

        // Inflate the layout for this fragment
        return view;
    }

    public void updateProgress(boolean checkValue, int id){

        MyDBHelper db = new MyDBHelper(getContext());

        if (checkValue){
            db.updatePersonalListChecked(id, 1);
        } else {
            db.updatePersonalListChecked(id, 0);
        }

        updateProgressBar();
    }

    public void updateProgressBar(){
        MyDBHelper db = new MyDBHelper(getContext());
        int total = db.getTotalPersonalList();
        int complete = db.getTotalCompletePersonalList();

        if(total != 0){
            int percentage = (complete * 100 / total);
            progress.setProgress(percentage);
        } else {
            progress.setProgress(0);
        }
    }

    public void initProgressBar(){
        //get total of records in table plus those which are ticked and find percentage
        //to set initial bar

        MyDBHelper db = new MyDBHelper(getContext());
        int total = db.getTotalPersonalList();
        int complete = db.getTotalCompletePersonalList();

        if(total != 0){
            int percentage = (complete * 100 / total);
            progress.setProgress(percentage);
        } else {
            progress.setProgress(0);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.personal_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_add:
                add_item();
                return true;

            case R.id.menu_item_delete:
                delete_all();
                return true;

            case R.id.menu_item_delete_complete:
                delete_complete();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add_item(){
        LayoutInflater inflater1 = LayoutInflater.from(getContext());
        View view1 = inflater1.inflate(R.layout.new_list_item, null);

        editText = (EditText)view1.findViewById(R.id.list_item);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setView(view1);

        alertDialog.setCancelable(false)
                .setPositiveButton("Add to list",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                userInput = editText.getText().toString();
                                valueCreate(userInput);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog1 = alertDialog.create();

        alertDialog1.show();
    }

    public void valueCreate(String item){
        MyDBHelper dbHelper = new MyDBHelper(getContext());
        String result = dbHelper.addPersonalListItem(item);
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        recreateFragment();
        updateProgressBar();
    }

    public void delete_all(){
        MyDBHelper dbHelper = new MyDBHelper(getContext());
        dbHelper.deletePersonalList();
        recreateFragment();
        updateProgressBar();
    }

    public void delete_complete(){
        MyDBHelper dbHelper = new MyDBHelper(getContext());
        String result = dbHelper.deletePersonalListComplete();
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        recreateFragment();
        updateProgressBar();
    }

    public void itemLongClick(int id){
        //set selected id
        selected_id = id;

        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.context_menu_list, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_delete:
                    delete();
                    return true;
                case R.id.menu_edit:
                    edit();
                    return true;
                default:
            }
            return false;
        }
    }

    public void delete(){
        MyDBHelper db = new MyDBHelper(getContext());
        db.deletePersonalListItem(selected_id);
        recreateFragment();
        updateProgressBar();
    }

    public void edit(){
        LayoutInflater inflater1 = LayoutInflater.from(getContext());
        View view1 = inflater1.inflate(R.layout.new_list_item, null);

        editText = (EditText)view1.findViewById(R.id.list_item);

        //get current text of item from database
        MyDBHelper db = new MyDBHelper(getContext());

        editText.setText(db.getCurrentPersonalListName(selected_id));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setView(view1);

        alertDialog.setCancelable(false)
                .setPositiveButton("Update item",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                userInput = editText.getText().toString();
                                valueEdit(userInput);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog1 = alertDialog.create();

        alertDialog1.show();
    }

    public void valueEdit(String item){
        MyDBHelper dbHelper = new MyDBHelper(getContext());
        String result = dbHelper.editPersonalListItem(item, selected_id);
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        recreateFragment();
        updateProgressBar();
    }

    public void recreateFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.attach(this);
        ft.commit();
    }

}
