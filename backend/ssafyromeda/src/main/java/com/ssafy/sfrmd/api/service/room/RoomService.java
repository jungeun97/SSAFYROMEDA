package com.ssafy.sfrmd.api.service.room;

import com.ssafy.sfrmd.api.domain.player.Player;
import com.ssafy.sfrmd.api.domain.player.PlayerRepository;
import com.ssafy.sfrmd.api.domain.room.Room;
import com.ssafy.sfrmd.api.domain.room.RoomRepository;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    private final PlayerRepository playerRepository;

//    HashSet<String> roomCodeSet=new HashSet<>(); // 방코드 set

    public String makeRoomCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        return generatedString;
    }

    public Room createRoom(long host, String roomCode) {

        // room 정보 저장
        Room room = new Room();
        room.setRoomHost(host);
        room.setRoomCnt(1);

        long roomSeq = roomRepository.save(room).getRoomSeq();

        // host 정보 저장
        Player player = new Player();
        player.setUserNo(host);
        player.setRoomCode(roomCode);
        playerRepository.save(player);

        return room;
    }

    public boolean updateRoom(String roomCode, Long userNo){
        // 방 DB에 인원수 추가
        try{
            Optional<Room> updateRoom=roomRepository.findByRoomCode(roomCode);

            int cnt=updateRoom.get().getRoomCnt();
            updateRoom.get().setRoomCnt(++cnt);

            roomRepository.save(updateRoom.get());

            return true;
        }catch (Exception e){
            return false;
        }
    }


    public Optional<Room> getRoomByRoomCode(String roomCode) {
        return roomRepository.findByRoomCode(roomCode);
    }


    public boolean deleteRoom(Long roomSeq, String roomCode) {
        try{
            roomRepository.deleteById(roomSeq);
//            roomCodeSet.remove(roomCode);

            return true;
        }catch (Exception e){
            return false;
        }
    }


}
