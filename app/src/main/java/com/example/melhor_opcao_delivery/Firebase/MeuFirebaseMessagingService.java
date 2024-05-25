package com.example.melhor_opcao_delivery.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.melhor_opcao_delivery.MainActivity;
import com.example.melhor_opcao_delivery.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MeuFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MeuFirebaseMsgService";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Método chamado quando uma mensagem FCM é recebida.
        Log.d(TAG, "De: " + remoteMessage.getFrom());

        // Verificar se a mensagem contém um payload de dados.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Payload de dados da mensagem: " + remoteMessage.getData());
        }

        // Verificar se a mensagem contém um payload de notificação.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Corpo da Notificação da Mensagem: " + remoteMessage.getNotification().getBody());
            enviarNotificacao(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        // Método chamado quando o token FCM é atualizado.
        Log.d(TAG, "Token atualizado: " + token);
        enviarRegistroParaServidor(token);
    }

    private void enviarRegistroParaServidor(String token) {
        // Implemente este método para enviar o token para o servidor do seu app.
        Log.d(TAG, "enviarRegistroParaServidor(" + token + ")");
    }

    private void enviarNotificacao(String mensagemCorpo) {
        // Criar uma intenção para abrir a MainActivity quando o usuário tocar na notificação.
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String canalId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder construtorNotificacao =
                new NotificationCompat.Builder(this, canalId)
                        .setSmallIcon(R.drawable.notif_icon) // Ícone da notificação
                        .setContentTitle(getString(R.string.fcm_message)) // Título da notificação
                        .setContentText(mensagemCorpo) // Corpo da notificação
                        .setAutoCancel(true) // Cancelar notificação quando o usuário tocar
                        .setPriority(NotificationCompat.PRIORITY_HIGH) // Alta prioridade
                        .setContentIntent(pendingIntent); // Intenção a ser aberta quando o usuário tocar

        NotificationManager gerenteNotificacao =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Para dispositivos com Android O e superior, configurar um canal de notificação.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String nomeCanal = getString(R.string.default_notification_channel_name);
            NotificationChannel canal = new NotificationChannel(canalId, nomeCanal, NotificationManager.IMPORTANCE_HIGH);
            gerenteNotificacao.createNotificationChannel(canal);
        }

        // Enviar a notificação.
        gerenteNotificacao.notify(0, construtorNotificacao.build());
    }
}

