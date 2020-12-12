package com.arslan6015.jofitcoach.ui.Contactus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arslan6015.jofitcoach.R;

public class ContactUsFragment extends Fragment {
    LinearLayout phone_layout,email_layout,whatsapp_layout,direction_layout,google_maps_layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);

        phone_layout = root.findViewById(R.id.phone_layout);
        email_layout = root.findViewById(R.id.email_layout);
        whatsapp_layout = root.findViewById(R.id.whatsapp_layout);
        direction_layout = root.findViewById(R.id.direction_layout);
        google_maps_layout = root.findViewById(R.id.google_maps_layout);

        phone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: +063 2759338"));
                startActivity(callIntent);
            }
        });

        email_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(openGmail(getContext()));
            }
        });

        whatsapp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp();
            }
        });

        direction_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlocationOnMap();
            }
        });

        google_maps_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlocationOnMap();
            }
        });

        return root;
    }

    private void getlocationOnMap() {
    }

    private Intent openGmail(Context context) {
        try
        {
            context.getPackageManager().getPackageInfo("com.google.android.gm",0);
            return new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:jo.fit.motivation@gmaioml.com"));
        }catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://mail.google.com/mail/u/0/#inbox?compose=GTvVlcSKhbkVLJGGQLVkCRmzGfMdQQCkWzgCWvHlCpmmLmtLKSMCCPGQKFmZrXjrnFBGrljkStjnG"));
        }
    }

    private void openWhatsApp() {
        String contact = "+063 2759338"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = getActivity().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}