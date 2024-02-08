package fi.dy.masa.litematica.network.packet;

import fi.dy.masa.litematica.Litematica;
import fi.dy.masa.litematica.Reference;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.malilib.interfaces.ICarpetHelloListener;
import fi.dy.masa.malilib.network.ClientNetworkPlayHandler;
import fi.dy.masa.malilib.network.payload.CarpetHelloPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;

public class CarpetHelloListener implements ICarpetHelloListener
{
    final String CARPET_HI = "69";
    final String CARPET_HELLO = "420";
    @Override
    public void receiveCarpetHello(NbtCompound data, ClientPlayNetworking.Context ctx)
    {
        String carpetVersion = data.getString(CARPET_HI);
        Litematica.debugLog("ICarpetListener#onCarpetPayload(): received Carpet Hello packet. (Carpet Server {})", carpetVersion);
        if (!DataManager.isCarpetServer())
            DataManager.setIsCarpetServer(true);

        // Send Hello packet back to server, tell them that this is Litematica :)
        NbtCompound response = new NbtCompound();
        response.putString(CARPET_HELLO, Reference.MOD_ID+"-"+Reference.MOD_VERSION);
        sendCarpetHello(response);
    }
    @Override
    public void sendCarpetHello(NbtCompound data)
    {
        CarpetHelloPayload payload = new CarpetHelloPayload(data);
        ClientNetworkPlayHandler.sendCarpetHello(payload);
    }
}