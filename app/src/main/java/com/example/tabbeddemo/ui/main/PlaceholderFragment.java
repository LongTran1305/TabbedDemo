package com.example.tabbeddemo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tabbeddemo.MainActivity;
import com.example.tabbeddemo.R;
import com.example.tabbeddemo.adapter.BookAdapter;
import com.example.tabbeddemo.dal.BookDB;
import com.example.tabbeddemo.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private BookDB db;
    private List<BookDTO> data = new ArrayList<>();
    private BookAdapter adapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }
    public void notifyBookDataChanged() {
//        adapter.notifyDataSetChanged(); // gửi lệnh cập nhật list view
        //Way to get TagName which generated by FragmentPagerAdapter
        String tagName = "android:switcher:" + R.id.view_pager + ":" + 1; // Your pager name & tab no of Second Fragment

        //Get SecondFragment object from FirstFragment
        PlaceholderFragment f2 = (PlaceholderFragment) getActivity().getSupportFragmentManager().findFragmentByTag(tagName);

        f2.data.clear();   // xoa data cũ
        f2.data.addAll(db.getBooks()); // doc du lieu moi tu db leni
//        Log.i("BOOK", "Total Book: " + data.size());

        //Then call your wish method from SecondFragment to update appropriate list
        f2.adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        db = new BookDB(getContext());
        data.addAll(db.getBooks());
        adapter = new BookAdapter(getContext(),data);

        int index = getArguments().getInt(ARG_SECTION_NUMBER);
        View root = inflater.inflate(MainActivity.tabsId[index],container,false);
        switch (index){
            case 0:
            {
                final EditText txtId = root.findViewById(R.id.txtId);
                final EditText txtTitle = root.findViewById(R.id.txtTitle);
                final EditText txtPrice = root.findViewById(R.id.txtPrice);
                Button btnCreate = root.findViewById(R.id.btnCreate);
                btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idCreate = txtId.getText().toString();
                        String titleCreate = txtTitle.getText().toString();
                        String priceCreate = txtPrice.getText().toString();

                        BookDTO bookCreate = new BookDTO(Integer.parseInt(idCreate),titleCreate,Integer.parseInt(priceCreate));
                        db.create(bookCreate);
                        data.clear();
                        data.addAll(db.getBooks());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"create successfull",Toast.LENGTH_LONG).show();
                        notifyBookDataChanged();
                    }
                });
                break;
            }
            case 1:{
                ListView listView = root.findViewById(R.id.listBook);
                listView.setAdapter(adapter);
                notifyBookDataChanged();

                break;
            }
            case 2:{
                Button btnSearch = root.findViewById(R.id.btnSearch);
                Button btnUpdate = root.findViewById(R.id.btnUpdate);
                final EditText txtID = root.findViewById(R.id.txtIdUpdate);
                final EditText txtTitle = root.findViewById(R.id.txtTitleUpdate);
                final EditText txtPrice = root.findViewById(R.id.txtPriceUpdate);
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchID =  txtID.getText().toString();
                        BookDTO bookDTO = db.getBook(Integer.parseInt(searchID));
                        if (bookDTO != null){
                            txtTitle.setText(bookDTO.getTxtTitle());
                            txtPrice.setText(bookDTO.getTxtPrice()+"");
                            txtID.setEnabled(false);
                        }
                    }
                });
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BookDTO updateBook = new BookDTO();
                        updateBook.setTxtID(Integer.parseInt(txtID.getText().toString()));
                        updateBook.setTxtTitle((txtTitle.getText().toString()));
                        updateBook.setTxtPrice(Integer.parseInt(txtPrice.getText().toString()));
                        db.update(updateBook);
                        Toast.makeText(getContext(),"Successfull",Toast.LENGTH_LONG).show();
                        notifyBookDataChanged();
                    }
                });
                break;
            }
            case 3:{
                Button btnDelete = root.findViewById(R.id.btnDelete);
                final EditText txtIdDelete = root.findViewById(R.id.txtIdDelete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idDelete = txtIdDelete.getText().toString();
                        if (idDelete.equals("")){
                            Toast.makeText(getContext(),"ID not found",Toast.LENGTH_LONG).show();
                        }else{
                            BookDTO bookDTO = db.getBook(Integer.parseInt(idDelete));
                            if(bookDTO != null){
                                db.delete(Integer.parseInt(idDelete));
                                Toast.makeText(getContext(),"Delete successfull",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

                break;
            }
        }
        return root;
    }
}