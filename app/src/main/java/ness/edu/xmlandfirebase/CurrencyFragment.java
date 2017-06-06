package ness.edu.xmlandfirebase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyFragment extends Fragment implements CurrencyDataSource.OnCurrencyArrivedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency, container, false);

        CurrencyDataSource.getCurrencies(this);

        return v;
    }

    @Override
    public void onCurrencyArrived(final List<CurrencyDataSource.Currency> data, final Exception e) {
        //No UI Updating on a secondary thread.
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e == null){
                    //All is good.
                    Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getContext(), "Error connecting", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
