package br.com.marcaosi.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.Serializable;

import br.com.marcaosi.agenda.R;
import br.com.marcaosi.agenda.dao.AlunoDAO;

/**
 * Created by marca on 27/09/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[])intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];

        String formato = (String)intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);

        if(dao.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS de aluno.", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        dao.close();
    }
}
