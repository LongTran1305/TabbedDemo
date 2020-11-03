package com.example.tabbeddemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tabbeddemo.R;
import com.example.tabbeddemo.dto.BookDTO;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    private Context ctx;
    private List<BookDTO> data;

    public BookAdapter(Context ctx, List<BookDTO> data) {
        this.ctx = ctx;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_item, null);
        }

        TextView lblId = convertView.findViewById(R.id.listBookId);
        TextView lblTitle = convertView.findViewById(R.id.listBookTitle);
        TextView lblPrice = convertView.findViewById(R.id.listBookPrice);

        //Lấy một item trong List và gán cho một đối tượng trong BookDto.
        BookDTO b = data.get(position);

        //Gán các thuộc tính của đối tượng 'b' cho lblId, lblTitle,lblPrice.
        lblId.setText(""+ b.getTxtID());
        lblTitle.setText(b.getTxtTitle());
        lblPrice.setText(String.valueOf(b.getTxtPrice()));

        return convertView;
    }
}
