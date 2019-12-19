package com.ly.demo.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

/**
 * @author xinre
 */
@Slf4j
public class BeanUtil {


    /**
     * 采用cglib BeanCopier 进行对象的复制（速度较快）
     *
     * @param sourceEntity      被复制的对象
     * @param targetEntityClass 新对象Class
     * @param <S>               源
     * @param <T>               目标
     * @return 目标
     */
    public static <S, T> T copier(S sourceEntity, Class<T> targetEntityClass) {
        T t;
        final BeanCopier beanCopier = BeanCopier.create(sourceEntity.getClass(), targetEntityClass, false);
        try {
            t = targetEntityClass.newInstance(); // javabean含有@builder注解这里会报错
            beanCopier.copy(sourceEntity, t, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return t;
    }

    public static <S, T> List<T> copier(List<S> sourceEntityList, Class<T> targetEntityClass) {

        if (CollectionUtils.isEmpty(sourceEntityList)) {
            return null;
        } else {
            List<T> r = Lists.newArrayListWithCapacity(sourceEntityList.size());
            final BeanCopier beanCopier = BeanCopier.create(sourceEntityList.get(0).getClass(), targetEntityClass, false);
            for (int i = 0; i < sourceEntityList.size(); i++) {
                try {
                    T t = targetEntityClass.newInstance();
                    beanCopier.copy(sourceEntityList.get(i), t, null);
                    r.add(t);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
            return r;
        }
    }


    public static <S, T> T convert(S sourceEntity, Class<T> targetEntityClass) {
        T t;
        try {
            t = targetEntityClass.newInstance();
            BeanUtils.copyProperties(t, sourceEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return t;
    }

    public static <S, T> List<T> convert(List<S> sourceEntityList, Class<T> targetEntityClass) {
        if (CollectionUtils.isEmpty(sourceEntityList)) {
            return null;
        } else {
            List<T> targetEntityList = Lists.newArrayListWithCapacity(sourceEntityList.size());
            for (int i = 0, size = sourceEntityList.size(); i < size; i++) {
                try {
                    T t = targetEntityClass.newInstance();
                    BeanUtils.copyProperties(t, sourceEntityList.get(i));
                    targetEntityList.add(t);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
            return targetEntityList;
        }
    }

    public static void main(String[] args) {
        /*UserDTO build = UserDTO.builder()
                .code("mfl")
                .deptId("001")
                .email("yf@qq.com")
                .remark("hgct")
                .status(1)
                .telephone("18812345678")
                .build();
        build.setId(IdGenerator.guid());
        build.setName("中国我爱你");
        build.setNameHeadChar(PinYinUtils.headChar(build.getName()));
        UserPO userPO = convert(build, UserPO.class);
        System.out.println("userPO = " + userPO);*/
    }
}
