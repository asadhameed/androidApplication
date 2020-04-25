package com.androidbook.crimicalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        @Override
        public void onClick(View v) {
            String make = mCrime.getmTitle() + "  status " + mCrime.ismSolved();
            Toast.makeText(getActivity(), make, Toast.LENGTH_LONG).show();
        }


        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getmTitle());
            mDateTextView.setText(crime.getmDate().toString());
        }


        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.crime_title_show);
            mDateTextView = itemView.findViewById(R.id.crime_data_show);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(inflater, parent,viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);

        }

        @Override
        public int getItemViewType(int position) {
            if(mCrimes.get(position).ismRequiresPolice())
                return R.layout.list_item_crime_police;
            else
                return R.layout.list_item_crime;

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        mCrimeRecyclerView.getAdapter().notifyItemMoved(0, 5);
        return v;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }
}
