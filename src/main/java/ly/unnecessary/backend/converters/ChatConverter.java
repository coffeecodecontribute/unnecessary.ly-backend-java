package ly.unnecessary.backend.converters;

import java.util.List;
import java.util.stream.Collectors;

import com.google.protobuf.InvalidProtocolBufferException;

import ly.unnecessary.backend.api.CommunityOuterClass.Chats;
import ly.unnecessary.backend.api.CommunityOuterClass.NewChat;
import ly.unnecessary.backend.entities.Channel;
import ly.unnecessary.backend.entities.Chat;
import ly.unnecessary.backend.entities.User;

/**
 * Chat converter
 */
public class ChatConverter {
    /**
     * Convert to external
     * 
     * @param internalChat
     * @return Chat
     */
    public ly.unnecessary.backend.api.CommunityOuterClass.Chat toExternal(Chat internalChat) {
        return ly.unnecessary.backend.api.CommunityOuterClass.Chat.newBuilder().setId(internalChat.getId())
                .setChannelId(internalChat.getChannel().getId()).setMessage(internalChat.getMessage())
                .setUserId(internalChat.getUser().getId()).build();
    }

    /**
     * Convert from new chat to internal
     * 
     * @param newChat
     * @return Chat
     */
    public Chat fromNewChatToInternal(NewChat newChat) {
        var chat = new Chat();

        var channel = new Channel();
        channel.setId(newChat.getChannelId());

        chat.setChannel(channel);
        chat.setMessage(newChat.getMessage());

        return chat;
    }

    /**
     * Convert from external to internal
     * 
     * @param externalChat
     * @return Chat
     */
    public Chat toInternal(ly.unnecessary.backend.api.CommunityOuterClass.Chat externalChat) {
        var chat = new Chat();

        chat.setId(externalChat.getId());

        chat.setMessage(externalChat.getMessage());
        var channel = new Channel();
        channel.setId(externalChat.getChannelId());

        var user = new User();
        user.setId(externalChat.getUserId());

        chat.setChannel(channel);
        chat.setUser(user);

        return chat;
    }

    /**
     * Convert from serialized to internal
     * 
     * @param byteChat
     * @return Chat
     * @throws InvalidProtocolBufferException
     */
    public Chat fromByteArrayToInternal(byte[] byteChat) throws InvalidProtocolBufferException {
        return this.toInternal(ly.unnecessary.backend.api.CommunityOuterClass.Chat.parseFrom(byteChat));
    }

    /**
     * Convert to serialized
     * 
     * @param internalChat
     * @return byte[]
     */
    public byte[] toByteArray(Chat internalChat) {
        return this.toExternal(internalChat).toByteArray();
    }

    /**
     * Convert from many internal to external
     * 
     * @param internalChats
     * @return Chats
     */
    public Chats fromManyToExternal(List<Chat> internalChats) {
        return Chats.newBuilder()
                .addAllChats(internalChats.stream().map(c -> this.toExternal(c)).collect(Collectors.toList())).build();
    }
}