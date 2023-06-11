package cos.audio;

import bin.apply.Repository;
import cos.file.FileItem;
import cos.file.FileToken;
import work.ResetWork;

public class Reset implements ResetWork, AudioToken, Repository {
    @Override
    public void reset() {
        checkModuleError("file");
        create(AUDIO, AudioItem.class, v -> new AudioItem((FileItem) v[0]), FileToken.FILE);

        methodWorks.add(AUDIO, SET_SPEED, f, AudioItem::setSpeed);
        methodWorks.add(AUDIO, SET_VOLUME, f, AudioItem::setVolume);
        methodWorks.add(AUDIO, SET_PITCH, f, AudioItem::setPitch);
        methodWorks.add(AUDIO, SET_RATE, f, AudioItem::setRate);
        methodWorks.add(AUDIO, SET_LOOP, i, AudioItem::setLoop);

        methodWorks.add(AUDIO, GET_SPEED, AudioItem::getSpeed, f);
        methodWorks.add(AUDIO, GET_VOLUME, AudioItem::getVolume, f);
        methodWorks.add(AUDIO, GET_PITCH, AudioItem::getPitch, f);
        methodWorks.add(AUDIO, GET_RATE, AudioItem::getRate, f);
        methodWorks.add(AUDIO, GET_LOOP, AudioItem::getLoop, i);

        methodWorks.add(AUDIO, START, AudioItem::start);
    }
}
