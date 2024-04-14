package com.isekai.ssgserver.s3.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class S3Service {

	private S3Client s3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName; // 버킷 이름을 클래스 필드로 설정

	@PostConstruct
	public void init() {
		Region region = Region.AP_NORTHEAST_2; // 리전 설정
		this.s3 = S3Client.builder()
			.region(region)
			.build();
	}

	/**
	 * S3 bucket의 객체 list 가져오기 (sout)
	 */
	public void listBucketObjects() {
		try {
			ListObjectsRequest listObjects = ListObjectsRequest
				.builder()
				.bucket(bucketName)
				.build();
			ListObjectsResponse res = s3.listObjects(listObjects);
			List<S3Object> objects = res.contents();
			for (S3Object myValue : objects) {
				System.out.print("\n The name of the key is " + myValue.key());
				System.out.print("\n The object is " + calKb(myValue.size()) + " KBs");
				System.out.print("\n The owner is " + myValue.owner());
				System.out.println();
			}

		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
	}

	/**
	 * 미리 서명된 url을 생성
	 * @param keyName photos/2021/trip1/picture.jpg라는 keyName은 photos/2021/trip1/ 폴더(디렉터리)에 picture.jpg 파일을 저장
	 * @param metadata 파일의 원본 생성 날짜, 파일을 업로드한 애플리케이션의 이름, 또는 사용자 지정 태그 같은 정보를 포함
	 * @return
	 */
	public String createPresignedUrl(String keyName, Map<String, String> metadata) {
		try (S3Presigner presigner = S3Presigner.create()) {

			PutObjectRequest objectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(keyName)
				.metadata(metadata)
				.build();

			PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
				.signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
				.putObjectRequest(objectRequest)
				.build();

			PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
			String myURL = presignedRequest.url().toString();

			System.out.println("Presigned URL to upload a file to: [{}]" + myURL);
			System.out.println("HTTP method: [{}]" + presignedRequest.httpRequest().method());

			return presignedRequest.url().toExternalForm();
		}
	}

	// convert bytes to kbs.
	public static long calKb(Long val) {
		return val / 1024;
	}
}
