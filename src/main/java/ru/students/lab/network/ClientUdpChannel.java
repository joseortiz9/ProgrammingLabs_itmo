package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.util.ListEntrySerializable;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс для создания UPD канала для клиента
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ClientUdpChannel extends AbsUdpSocket {

    protected static final Logger LOG = LogManager.getLogger(ClientUdpChannel.class);

    protected DatagramChannel channel;
    protected SocketAddress addressServer;
    protected boolean connected;
    protected boolean requestSent;

    public ClientUdpChannel() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(null);
        addressServer = null;
    }

    /**
     * Функция для подключения к серверу по адресу
     * @param addressServer - адрес сервера
     */
    public void tryToConnect(InetSocketAddress addressServer) {
        this.addressServer = addressServer;
        sendCommand("connect");
        System.out.println("Trying to reach the server...");
        LOG.info("Trying to reach the server...");
    }
    /**
     * Функция для отправки байт-буфера
     * @param content - байт-буфер
     */

    public void sendDatagram(ByteBuffer content) throws IOException {
        channel.send(content, addressServer);
        this.requestSent = true;
        LOG.info("sent datagram to " + addressServer);
    }

    /**
     * Функция для получения датаграммы и записи ее в буфер
     * @param buffer - буфер, в который записывается датаграмма
     * @return ret - адрес сервера
     */
    public SocketAddress receiveDatagram(ByteBuffer buffer) throws IOException {
        SocketAddress ret;
        ret = channel.receive(buffer);
        return ret;
    }
    /**
     * Функция для сериализации и отправки команды
     * @param command - отправляемая команда
     */
    public void sendCommand(Object command) {
        try(ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayStream)) {

            objectStream.writeObject(command);
            LOG.info("send object " + command);
            final ByteBuffer objectBuffer = ByteBuffer.wrap(byteArrayStream.toByteArray());

            sendDatagram(objectBuffer);
            Thread.sleep(500);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            LOG.error("", e);
        }
    }

    /**
     * Функция для отключения от сервера
     */
    public void disconnect() {
        try {
            channel.close();
        } catch (IOException e) {
            LOG.error("Error trying to disconnect, doing a forced out", e);
            System.exit(-1);
        }
    }
    /**
     * Функция для проверки подключения к серверу
     */
    public boolean isConnected() {
        return addressServer != null && connected;
    }
    /**
     * Функция для задания подключения/отключения к серверу
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    /**
     * Функция для задания отключения от сервера
     */
    public void setConnectionToFalse() {
        this.addressServer = null;
        this.connected = false;
    }
    /**
     * Функция получения информации о том, был ли отправлен ответ
     * @return boolean requestSent
     */
    public boolean requestWasSent() {
        return requestSent;
    }
    /**
     * Функция для получения данных
     * @return processResponse полученные данные
     */
    public Object receiveData() throws IOException, ClassNotFoundException {
        final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
        final SocketAddress addressFromServer = receiveDatagram(buf);
        buf.flip();

        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);

        if (bytes.length < 1)
            return null;

        requestSent = false;
        if (bytes.length < AbsUdpSocket.DATA_SIZE)
            return processResponse(bytes);
        else
            throw new EOFException();
    }
    /**
     * Функция для десериализации полученных данных
     * @param petitionBytes - данные
     * @return obj - объект десериализованных данных
     */
    private Object processResponse(byte[] petitionBytes) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(petitionBytes))) {
            final Object obj = stream.readObject();
            LOG.info("received object: " + obj);
            if (obj == null)
                throw new ClassNotFoundException();
            return obj;
        }
    }
    /**
     * Функция для вывода объектов коллекции
     * @param obj- коллекция с объектами
     */
    public void printObj(Object obj) throws ClassNotFoundException {
        if (obj instanceof String)
            System.out.println(obj);
        else if (obj instanceof List) {
            if (((List) obj).get(0) instanceof ListEntrySerializable) {
                ((List<ListEntrySerializable>) obj).stream().forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getDragon().toString()));
                System.out.println("Elements found: "+ ((List) obj).size());
            }else {
                for (Object objFromScript: (List)obj) {
                    if (objFromScript instanceof String)
                        System.out.println(objFromScript);
                    else if (objFromScript instanceof List) {
                        ((List<ListEntrySerializable>) objFromScript).stream().forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getDragon().toString()));
                        System.out.println("Elements found: "+ ((List) objFromScript).size());
                    }
                }
            }
        } else
            throw new ClassNotFoundException();
    }
}
