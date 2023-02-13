import { useState } from "react";
import OurTeamVid from "components/room/OurTeamVid";
import Map from "components/room/Map";
import TheirTeamVid from "components/room/TheirTeamVid";
import styled from "styled-components";
import MyButton from "components/common/MyButton";


const Container = styled.div`
  display: flex;
  justify-content: center;
  border: 1px solid black;
  border-radius: 20px;
  margin: 20px;
`;

const Page = styled.div`
  height: 100%;
  width: 100%;
`;

const GameStartButton = styled.div`
  
`;

const GamePage = ({
  ov,
  session,
  mySessionId,
  mainStreamManager,
  publisher,
  subscribers,
  isMike,
  isCamera,
  isSpeaker,
  myUserName,
  currentVideoDevice,
  initRoom,
  joinRoom,
  leaveSession,
  deleteSubsciber,
  userNickname,
  userNo,
  team1Members,
  team2Members,
}) => {
  
  const [ isGameStarted, SetIsGameStarted ] = useState(undefined);

  const GameStart = () => {
    SetIsGameStarted(true);
  };
  
  return (
    <Page>
      <Container>
        <OurTeamVid
          streamManager={mainStreamManager}
          subscribers={subscribers}
          publisher={publisher}
          userNickname={userNickname}
          userNo={userNo}
          team1Members={team1Members}
        />
        { isGameStarted !== false ? (<Map/>)
          : (
          <GameStartButton>
            <MyButton
                  lang={"Korean"}
                  text={"게임 시작"}
                  type={"is-success"}
                  onClick={GameStart}
                />
          </GameStartButton>
          )
        }
        <TheirTeamVid
          streamManager={mainStreamManager}
          subscribers={subscribers}
          publisher={publisher}
          userNickname={userNickname}
          userNo={userNo}
          team2Members={team2Members}
        />
      </Container>
    </Page>
  );
};

export default GamePage;